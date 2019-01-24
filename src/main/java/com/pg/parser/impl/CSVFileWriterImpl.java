package com.pg.parser.impl;

import com.pg.parser.CSVFileWriter;
import com.pg.parser.Column;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Implementation class for {@link CSVFileWriter} interface
 */
public class CSVFileWriterImpl implements CSVFileWriter{

    private static final Logger logger = LoggerFactory.getLogger(CSVFileWriterImpl.class);

    /**
     * The delimiter to use in the CSV format.
     */
    private static final String DEFAULT_DELIMITER = ",";

    /**
     * The default endline for csv file.
     */
    private static final String DEFAULT_ENDLINE = "\r\n";

    /**
     * The variable to output csv file.
     */
    private final Writer writer;

    /**
     * The delimiter for csv file.
     */
    private String delimiter = DEFAULT_DELIMITER;

    /**
     * The endline for csv file.
     */
    private String endline = DEFAULT_ENDLINE;


    public CSVFileWriterImpl(Writer writer, String delimiter, String endline) {
        this.writer = writer;
        this.delimiter = delimiter;
        this.endline = endline;
    }

    static String getQuotedString(String delimiter, String trimmedText) {
        boolean containsDelimiter = stringContainsItemFromList(trimmedText, delimiter, "\n", "\t", "\r", "\"");
        String trimmedTextWithoutQuotesDuplicates = trimmedText.replace("\"", "\"\"");
        if (containsDelimiter && trimmedTextWithoutQuotesDuplicates.length() != 0) {
            return "\"" + trimmedTextWithoutQuotesDuplicates + "\"";
        }
        return trimmedTextWithoutQuotesDuplicates;
    }

    private static boolean stringContainsItemFromList(String inputString, String... items) {
        for (String item : items) {
            if (inputString.contains(item)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void writeColumnNames(List<Column> columns) throws IOException {
        for (int i = 0; i < columns.size(); i++) {
            writer.write(getQuotedString(getDelimiter(), columns.get(i).getName()));
            if (i != columns.size() - 1) {
                writer.write(getDelimiter());
            }
        }
        writer.write(getEndline());
        writer.flush();
    }


    @Override
    public void writeRow(List<Column> columns, String row) throws IOException {
        if (row == null || row.isEmpty()) { //skip any empty row
            return;
        }
        List<String> valuesToPrint = processRow(row, columns);
        for (int currentColumnIndex = 0; currentColumnIndex < columns.size(); currentColumnIndex++) {
            writer.write(getQuotedString(getDelimiter(), valuesToPrint.get(currentColumnIndex)));
            if (currentColumnIndex != columns.size() - 1) {
                writer.write(getDelimiter());
            }
        }
        writer.write(getEndline());
        writer.flush();
    }

    public List<String> processRow(String row, List<Column> columns) throws IOException {
        List<String> listOfRowItems = new ArrayList<>();
        int beginIndex = 0;
        for (Column col : columns) {
            int endIndex = beginIndex + col.getLength();
            endIndex = endIndex > row.length() ? row.length() : endIndex;
            listOfRowItems.add(processDataItem(row.substring(beginIndex, endIndex), col.getType()).trim());
            beginIndex = endIndex;
        }
        return listOfRowItems;
    }

    public Writer getWriter() {
        return writer;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public String getEndline() {
        return endline;
    }


    private String processDataItem(String dataItem, String columnType) throws IOException {
        if(columnType.equalsIgnoreCase("Date")) {
            dataItem = formatDateTypeDataItem(dataItem);
        }
        return dataItem;
    }

    private String formatDateTypeDataItem(String dataItem) throws IOException {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = inputFormat.parse(dataItem);
            return outputFormat.format(date);
        } catch (ParseException e) {
            logger.error("Unable to parse date {}", dataItem);
            throw new IOException("Unable to parse dataitem " + dataItem);
        }
    }

}
