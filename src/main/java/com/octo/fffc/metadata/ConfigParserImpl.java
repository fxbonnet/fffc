package com.octo.fffc.metadata;

import com.octo.fffc.exception.InvalidInputException;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * The class generates a list of {@link ColumnDefinition} by reading the metadata file
 *
 * @author anoop.shiralige
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class ConfigParserImpl implements ConfigParser {

    private static final Logger logger = getLogger(ConfigParserImpl.class);
    private final ColumnDefinitionExtractor extractor;

    public ConfigParserImpl(ColumnDefinitionExtractor extractor) {
        this.extractor = extractor;
    }

    @Override
    public List<ColumnDefinition> parseFile(String filePath) {
        List<ColumnDefinition> columns = new ArrayList<>();
        long lineNo = 1;
        try (FileInputStream inputStream = new FileInputStream(filePath);
             Scanner sc = new Scanner(inputStream)) {
            while (sc.hasNextLine()) {
                columns.add(extractor.extractDefinitions(sc.nextLine()));
                lineNo++;
            }
        } catch (IOException e) {
            String msg = format("File %s either does not exist or is not readable", filePath);
            logger.error(msg, e);
            return emptyList();
        } catch (InvalidInputException ex) {
            logger.error("Couldn't parse the line:{} of the metadata file", lineNo, ex);
            return emptyList();
        }
        return unmodifiableList(columns);
    }
}