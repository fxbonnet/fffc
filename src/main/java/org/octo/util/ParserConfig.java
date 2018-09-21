package org.octo.util;

import com.univocity.parsers.fixed.FixedWidthFields;
import com.univocity.parsers.fixed.FixedWidthParserSettings;
import org.octo.mapper.MetadataComponents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParserConfig {

    private static Logger logger = LoggerFactory.getLogger(ParserConfig.class);

    public static FixedWidthParserSettings getParserSettings(MetadataComponents metadataComponents) {
        logger.debug("Configuring Parser settings");
        FixedWidthParserSettings parserSettings =
                new FixedWidthParserSettings(new FixedWidthFields(metadataComponents.getFieldNames(), metadataComponents.getFieldsSize()));

        parserSettings.getFormat().setLineSeparator("\n");
        parserSettings.setSkipTrailingCharsUntilNewline(true);
        parserSettings.setRecordEndsOnNewline(true);
        parserSettings.setHeaderExtractionEnabled(false);
        return parserSettings;
    }

}
