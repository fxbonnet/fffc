package com.octo.fffc.converter;

import com.octo.fffc.exception.InvalidInputException;
import com.octo.fffc.formatter.CsvFormatter;
import com.octo.fffc.handlers.BufferedCsvWriter;
import com.octo.fffc.handlers.BufferedFixedFileFormatReader;
import com.octo.fffc.handlers.CsvWriter;
import com.octo.fffc.handlers.FixedFileFormatReader;
import com.octo.fffc.metadata.ColumnDefinition;
import com.octo.fffc.metadata.ConfigParser;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static java.lang.System.lineSeparator;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * The class is responsible for converting the input string into a csv string
 *
 * @author anoop.shiralige
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class Converter {

    private static final Logger logger = getLogger(Converter.class);
    private final CsvFormatter formatter;
    private final ConfigParser parser;
    private final Configurator config;

    public Converter(CsvFormatter formatter,
                     ConfigParser parser,
                     Configurator config) {
        this.formatter = formatter;
        this.parser = parser;
        this.config = config;
    }

    /**
     * @param inputArguments
     */
    public void convert(InputArguments inputArguments) throws Exception {
        List<ColumnDefinition> definitions = parser.parseFile(inputArguments.getConfigFile());

        if (definitions.size() < 1) {
            logger.error("Aborting the process !!!");
            return;
        }

        try (FixedFileFormatReader rd = new BufferedFixedFileFormatReader(inputArguments.getInputFile());
             CsvWriter wr = new BufferedCsvWriter(inputArguments.getOutputFile(), config.getFieldDelimiter())) {
            String inputLine;

            wr.write(getHeader(definitions));

            long lineNo = 1;
            while ((inputLine = rd.readLine()) != null) {
                logger.trace("input line : {}", inputLine);
                String[] output = formatter.format(inputLine, definitions);
                if (output.length > 0) {
                    logger.trace("transformed line : {} ", StringUtils.arrayToDelimitedString(output, config.getFieldDelimiter()));
                    wr.write(output);
                } else {
                    logger.error("The above issue was found at lineNo {}", lineNo);
                }
                lineNo++;
            }

            // Make sure that every data is written to the output file.
            wr.flush();
            logger.info("Transformation complete !!");
        } catch (InvalidInputException | IOException e) {
            logger.error(e.getMessage());
            logger.debug("", e);
        }
    }

    private String[] getHeader(List<ColumnDefinition> columns) {
        return columns.stream().map(column -> column.getName()).toArray(String[]::new);
    }
}
