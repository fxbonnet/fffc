package octo.util;

import octo.model.ColumnType;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by anjana on 27/05/18.
 */
public class DataFormatter {
    public static final Logger LOG = Logger.getLogger(DataFormatter.class);

    public static final String INPUT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String OUTPUT_DATE_FORMAT = "dd/MM/yyyy";

    public static String formatDate(String inputDate) {
        String formattedDate = inputDate;
        if (StringUtils.isNotBlank(inputDate)) {
            LocalDate input = LocalDate.parse(inputDate.trim(), DateTimeFormatter.ofPattern(INPUT_DATE_FORMAT));
            formattedDate = input.format(DateTimeFormatter.ofPattern(OUTPUT_DATE_FORMAT));
        }
        LOG.trace("Formatted input date " + inputDate + " to " + formattedDate);
        return formattedDate;
    }

    public static String formatString(String inputString) {
        String formattedString = inputString;
        if (StringUtils.isNotEmpty(inputString)) {
            formattedString = inputString.trim();
            if (formattedString.contains(",")) {
                formattedString = "\"" + formattedString + "\"";
            }
        }
        LOG.trace("Formatted input string " + inputString + " to " + formattedString);
        return formattedString;
    }

    public static String formatData(String inputData, ColumnType columnType) {
        switch (columnType) {
            case DATE:
                return DataFormatter.formatDate(inputData);
            case STRING:
                return DataFormatter.formatString(inputData);
            case NUMERIC:
                return DataFormatter.formatString(inputData);
            default:
                return null;
        }
    }
}
