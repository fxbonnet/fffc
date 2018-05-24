package com.octo.fffc;

import com.octo.fffc.model.transformer.FFFCTransformer;
import com.octo.fffc.model.transformer.Transformer;
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
 * Application main class implementation.
 *
 * @author bharath.raghunathan
 * @version 1.0.0
 * @since 1.0.0
 */
public class FixedFileConvertor {

    private static final Logger logger = LoggerFactory.getLogger(FixedFileConvertor.class);

    public static void main(String[] args) {
        Options options = new Options();
        prepareOptions(options);

        CommandLineParser commandLineParser = new DefaultParser();
        HelpFormatter helpFormatter = new HelpFormatter();

        try {
            CommandLine cmd = commandLineParser.parse(options, args);
            String metadataFile = cmd.getOptionValue("metadata");
            String inputFile = cmd.getOptionValue("input");
            String outputFile = cmd.getOptionValue("output");

            Transformer transformer = new FFFCTransformer(metadataFile,inputFile,outputFile);
            transformer.transform();
        } catch (ParseException e) {
            logger.error("Unable to parse input options");
            helpFormatter.printHelp(FixedFileConvertor.class.getCanonicalName(),options);
            System.exit(1);
        }
    }

    private static void prepareOptions(Options options) {
        Option metaDataOption = new Option("m", "metadata", true, "metadata file path");
        metaDataOption.setRequired(true);
        options.addOption(metaDataOption);

        Option inputDataOption = new Option("i", "input", true, "input file path");
        inputDataOption.setRequired(true);
        options.addOption(inputDataOption);

        Option outputDataOption = new Option("o", "output", true, "output file path");
        outputDataOption.setRequired(true);
        options.addOption(outputDataOption);
    }
}
