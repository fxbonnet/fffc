package com.octo.fffc.model.formatter;

import com.octo.fffc.exceptions.FormatterException;
import com.octo.fffc.model.metadata.DataType;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;

import java.util.function.Function;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.joda.time.format.DateTimeFormat.forPattern;

/**
 * Factory class to create formatter functions.
 *
 * @author bharath.raghunathan
 * @version 1.0.0
 * @since 1.0.0
 */
public final class FormatterFactory {

    private FormatterFactory() {
    }

    public static Function<String, String> formatter(DataType dataType) {

        switch (dataType) {
            case DATE:
                return new DateFormatter();
            default:
                return new StringFormatter();
        }
    }

    /**
     * Formatter for formatting date from yyyy-MM-dd format to dd/MM/yyyy format.
     *
     * @author bharath.raghunathan
     * @version 1.0.0
     * @since 1.0.0
     */
    private static final class DateFormatter implements Function<String, String> {

        private static final String INPUT_FORMAT = "yyyy-MM-dd";
        private static final String OUTPUT_FORMAT = "dd/MM/yyyy";

        private final DateTimeFormatter inputFormatter;
        private final DateTimeFormatter outputFormatter;

        private DateFormatter() {
            this.inputFormatter = forPattern(INPUT_FORMAT);
            this.outputFormatter = forPattern(OUTPUT_FORMAT);
        }

        @Override
        public String apply(String input) {

            if (isBlank(input)) {
                throw new FormatterException(format("Cannot Parse input date [%s]. Invalid Format", input));
            }

            try {
                DateTime inputDate = inputFormatter.parseDateTime(input.trim());
                return outputFormatter.print(inputDate);
            } catch (IllegalArgumentException ex) {
                throw new FormatterException(format("Cannot Parse input date [%s]. Invalid Format", input), ex);
            }
        }
    }

    /**
     * Formatter for formatting input string.
     * This formatter trims the empty spaces from the string and returns the string.
     *
     * @author bharath.raghunathan
     * @version 1.0.0
     * @since 1.0.0
     */
    private static final class StringFormatter implements Function<String, String> {
        @Override
        public String apply(String input) {
            if (input == null) {
                throw new FormatterException("Invalid Input. Input cannot be null");
            }
            return input.trim();
        }
    }
}
