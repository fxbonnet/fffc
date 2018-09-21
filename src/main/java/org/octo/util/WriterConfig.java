package org.octo.util;

import com.univocity.parsers.csv.CsvFormat;
import com.univocity.parsers.csv.CsvWriterSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WriterConfig {

    public static CsvWriterSettings csvWriterSeting;
    private static Logger logger = LoggerFactory.getLogger(ParserConfig.class);

    public static CsvWriterSettings getCsvWriterSettings() {
        logger.debug("Configuring CSV Writer settings");
        csvWriterSeting = new CsvWriterSettings();
        csvWriterSeting.setQuoteAllFields(true);
        CsvFormat format = new CsvFormat();
        format.setDelimiter(',');
        csvWriterSeting.setFormat(format);
        return csvWriterSeting;
    }
}
