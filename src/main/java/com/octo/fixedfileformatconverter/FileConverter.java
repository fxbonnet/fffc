/*
 * Copyright 2018 OCTO Technology.
 */
package com.octo.fixedfileformatconverter;

import com.octo.fixedfileformatconverter.exceptions.InvalidDataFormatException;
import com.octo.fixedfileformatconverter.exceptions.InvalidInputOutputFormatException;
import com.octo.fixedfileformatconverter.exceptions.InvalidMetaDataException;
import com.octo.fixedfileformatconverter.impl.DefaultColumnMetaData;
import com.octo.fixedfileformatconverter.impl.DefaultConverter;
import com.octo.fixedfileformatconverter.impl.Pair;
import com.octo.fixedfileformatconverter.impl.WrappedWriterCSV;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * File Converter.
 *
 * Converts data from one format in a source file, to another format in a destination file, with the help of meta data
 * describing the format of the data in the source.
 *
 * @author Mark Zsilavecz
 */
public class FileConverter
{

    /**
     * Logging instance.
     */
    public static final Logger LOG = LoggerFactory.getLogger(FileConverter.class);
    /**
     * Characters for CSV formatting.
     */
    public static final char CHAR_SEPARATOR;
    public static final char CHAR_QUOTE;
    public static final char CHAR_ESCAPE;
    public static final String CHAR_NEWLINE;
    /**
     * Options.
     */
    private static final String OPT_IN;
    private static final String OPT_OUT;
    private static final String OPT_META;
    private static final String OPT_FMT_IN;
    private static final String OPT_FMT_OUT;
    /**
     * Map of converters, that can be looked up by the specified input and output format.
     */
    private final Map<Pair<InputFormat, OutputFormat>, Converter> converters;

    static
    {
        CHAR_SEPARATOR = ',';
        CHAR_QUOTE = '"';
        CHAR_ESCAPE = '\\';
        CHAR_NEWLINE = "\r\n";

        OPT_IN = "i";
        OPT_OUT = "o";
        OPT_META = "m";
        OPT_FMT_IN = "u";
        OPT_FMT_OUT = "c";

    }

    /**
     * Default constructor.
     */
    public FileConverter()
    {
        converters = new HashMap<>(2);
        converters.put(new Pair<>(InputFormat.FIXED_FORMAT, OutputFormat.CSV), new DefaultConverter());
        // Add other converters as required.
    }

    /**
     * Process the meta data and input data files and writes out a converted file containing the converted data.
     *
     * @param meta the meta data file.
     * @param input the input data file.
     * @param output the output data file.
     * @param inFormat the input format.
     * @param outFormat the output format.
     *
     * @throws InvalidInputOutputFormatException if there there is no converter for the specified input-output formats.
     * @throws InvalidMetaDataException if the meta data is malformed or not accessible.
     * @throws InvalidDataFormatException if the input data is malformed or not accessible.
     * @throws IOException if the output data file could not be written to.
     */
    public void process(Path meta, Path input, Path output, InputFormat inFormat, OutputFormat outFormat)
      throws InvalidInputOutputFormatException, InvalidMetaDataException, InvalidDataFormatException, IOException
    {
        Pair<InputFormat, OutputFormat> key = new Pair<>(inFormat, outFormat);
        if (!converters.containsKey(key))
        {
            throw new InvalidInputOutputFormatException("Invalid combination of input and output formats.");
        }

        Converter converter = converters.get(key);
        try
        {
            // TODO: select meta data type based on input format type.
            List<DefaultColumnMetaData> metaData = readMetadata(meta);
            switch (outFormat)
            {
                case CSV:
                    performConversionCSV(metaData, input, output, converter);
                    break;
                default:
                    break;
            }
        }
        catch (InvalidMetaDataException e)
        {
            LOG.error("The meta data file was invalid.", e);
            throw e;
        }
        catch (InvalidDataFormatException e)
        {
            LOG.error("The data file was invalid.", e);
            throw e;
        }
        catch (IOException e)
        {
            LOG.error("The data file was not accessible.", e);
            throw e;
        }
    }

    /**
     * Reads the meta data file
     */
    private List<DefaultColumnMetaData> readMetadata(Path metadata) throws InvalidMetaDataException
    {
        final CSVParser parser = new CSVParserBuilder().
          withSeparator(CHAR_SEPARATOR).
          withQuoteChar(CHAR_QUOTE).
          withEscapeChar(CHAR_ESCAPE).
          build();
        int i = 0;
        try (BufferedReader reader = Files.newBufferedReader(metadata, StandardCharsets.UTF_8);
             CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(parser).build();)
        {
            List<DefaultColumnMetaData> columnMetaData = new LinkedList<>();
            String[] line;
            while ((line = csvReader.readNext()) != null)
            {
                i++;
                columnMetaData.add(DefaultColumnMetaData.from(line));
            }
            return new ArrayList<>(columnMetaData);
        }
        catch (IOException e)
        {
            throw new InvalidMetaDataException(String.format("Unable to read the meta data file at: %s",
                                                             metadata.toAbsolutePath()));
        }
        catch (InvalidMetaDataException e)
        {
            throw new InvalidMetaDataException(String.format("The meta data file format is invalid, at line: %d.", i));
        }
    }

    private <T extends DefaultColumnMetaData, U> void performConversionCSV(List<T> columns,
                                                                           Path input,
                                                                           Path output,
                                                                           Converter<T, String[]> converter)
      throws InvalidDataFormatException, IOException
    {
        try (BufferedReader reader = Files.newBufferedReader(input, StandardCharsets.UTF_8);
             WrappedWriter<String[]> writer = new WrappedWriterCSV(output);)
        {
            String line = null;
            try
            {
                String[] header = columns.stream()
                  .map((e) -> e.getName())
                  .collect(Collectors.toList())
                  .toArray(new String[columns.size()]);
                writer.write(header);
                while ((line = reader.readLine()) != null)
                {
                    String[] convertedLine = converter.convert(line, columns);
                    writer.write(convertedLine);
                }
            }
            catch (InvalidDataFormatException e)
            {
                LOG.error("Unable to convert file.", e);
                throw new InvalidDataFormatException(String.format("Unable to convert line: %s - %s",
                                                                   line, e.getMessage()));
            }
        }
        catch (IOException e)
        {
            LOG.error("Unable to convert file.", e);
            throw new IOException(String.format("Unable to access file: %s - %s", output, e.getMessage()));
        }

    }

    /**
     * Entry point.
     *
     * @param args arguments.
     */
    public static void main(String[] args)
    {

        Options options = createOptions();
        try
        {
            Optional<Arguments> optArgs = parseOptions(options, args);
            if (optArgs.isPresent())
            {
                Arguments arguments = optArgs.get();
                System.out.printf("Using arguments: %s%n", arguments);
                FileConverter converter = new FileConverter();
                converter.process(arguments.getMeta(),
                                  arguments.getInput(),
                                  arguments.getOutput(),
                                  arguments.getInputFormat(),
                                  arguments.getOutputFormat());
                //System.exit(0);
            }
            else
            {
                printHelpAndExit("ERROR: ", options);
            }
        }
        catch (IllegalArgumentException e)
        {
            printHelpAndExit(String.format("ERROR: Invalid argument: %s%n", e.getMessage()), options);

        }
        catch (InvalidInputOutputFormatException e)
        {
            printHelpAndExit(String.format("ERROR: Invalid option: %s", e.getMessage()), options);
        }
        catch (InvalidMetaDataException e)
        {
            printHelpAndExit(String.format("ERROR: Malformed or missing meta data: %s", e.getMessage()), options);
        }
        catch (InvalidDataFormatException | IOException e)
        {
            printHelpAndExit(String.format("ERROR: Malformed or missing input data: %s", e.getMessage()), options);
        }
    }

    private static Options createOptions()
    {
        Options options = new Options();
        options.addOption(Option.builder(OPT_IN).longOpt("input")
          .desc("The input data file.")
          .hasArg()
          .argName("INPUT")
          .build());

        options.addOption(Option.builder(OPT_OUT).longOpt("output")
          .desc("The output data file.")
          .hasArg()
          .argName("OUTPUT")
          .build());

        options.addOption(Option.builder(OPT_META).longOpt("metadata")
          .desc("The meta data file to use.")
          .hasArg()
          .argName("METADATA")
          .build());

        options.addOption(Option.builder(OPT_FMT_OUT).longOpt("converted")
          .desc("The converted/output format to use. Default: CSV")
          .hasArg()
          .optionalArg(true)
          .argName("CONVERTED")
          .build());

        options.addOption(Option.builder(OPT_FMT_IN).longOpt("uncoverted")
          .desc("The unconverted/input format to use. Default: FIXED_FILE")
          .hasArg()
          .optionalArg(true)
          .argName("UNCONVERTED")
          .build());
        return options;
    }

    private static Optional<Arguments> parseOptions(Options options, String[] args) throws IllegalArgumentException
    {
        try
        {
            CommandLineParser parser = new DefaultParser();
            CommandLine line = parser.parse(options, args);
            return Optional.of(Arguments.from(line));
        }
        catch (ParseException e)
        {
            LOG.error("Unexpected exception.", e);
            throw new IllegalArgumentException("Unable to parse arguments.");
        }
    }

    private static void printHelpAndExit(String msg, Options options)
    {
        HelpFormatter help = new HelpFormatter();
        help.printHelp("FixedFileFormatConverter", msg, options, "", true);
        throw new IllegalStateException();
    }

    /**
     * Arguments.
     *
     * Holds the arguments and options passed to the file converter from the commandline.
     */
    private static class Arguments
    {

        private final Path input;
        private final Path meta;
        private final Path output;
        private final InputFormat inFormat;
        private final OutputFormat outFormat;

        public static Arguments from(CommandLine line) throws IllegalArgumentException
        {
            Path meta;
            Path input;
            Path output;

            if (line.hasOption(OPT_META))
            {
                meta = Paths.get(line.getOptionValue(OPT_META));
            }
            else
            {
                throw new IllegalArgumentException("Meta data file was not specified.");
            }

            if (line.hasOption(OPT_IN))
            {
                input = Paths.get(line.getOptionValue(OPT_IN));
            }
            else
            {
                throw new IllegalArgumentException("Input data file was not specified.");
            }

            if (line.hasOption(OPT_OUT))
            {
                output = Paths.get(line.getOptionValue(OPT_OUT));
            }
            else
            {
                throw new IllegalArgumentException("Output data file was not specified.");
            }

            InputFormat inFormat = InputFormat.of(
              line.getOptionValue(OPT_FMT_IN, InputFormat.FIXED_FORMAT.toString())
              .toUpperCase(Locale.getDefault()));

            OutputFormat outFormat = OutputFormat.of(
              line.getOptionValue(OPT_FMT_OUT, OutputFormat.CSV.toString())
              .toUpperCase(Locale.getDefault()));

            return new Arguments(input, meta, output, inFormat, outFormat);
        }

        public Path getInput()
        {
            return input;
        }

        public Path getMeta()
        {
            return meta;
        }

        public Path getOutput()
        {
            return output;
        }

        public InputFormat getInputFormat()
        {
            return inFormat;
        }

        public OutputFormat getOutputFormat()
        {
            return outFormat;
        }

        @Override
        public String toString()
        {
            return String.format("Arguments[input=%s, meta=%s, output=%s, inFormat=%s, outFormat=%s]", input, meta, output, inFormat, outFormat);
        }

        private Arguments(Path input, Path meta, Path output, InputFormat inFormat, OutputFormat outFormat)
        {
            this.input = input;
            this.meta = meta;
            this.output = output;
            this.inFormat = inFormat;
            this.outFormat = outFormat;
        }

    }
}
