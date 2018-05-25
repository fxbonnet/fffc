package com.octo.fffc.formatter;

import com.octo.fffc.Configurator;
import com.octo.fffc.metadata.ColumnDefinition;
import com.octo.fffc.metadata.DataType;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.octo.fffc.formatter.FormatterUtils.convertDate;
import static com.octo.fffc.formatter.FormatterUtils.isValidNumeric;
import static java.util.Optional.empty;
import static org.hibernate.validator.internal.util.StringHelper.join;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Responsible for converting the input into a {@link Configurator#fieldDelimiter} string
 *
 * @author anoop.shiralige
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class CsvFormatter implements IFormatter {

    private final Configurator config;
    private static final Logger logger = getLogger(CsvFormatter.class);

    public CsvFormatter(Configurator config) {
        this.config = config;
    }

    public Optional<String> format(String input, List<ColumnDefinition> definitions) {
        String[] output = new String[definitions.size()];
        int length = 0;
        int index = 0;
        for (ColumnDefinition definition : definitions) {
            Optional<String> subString = getSubString(input, length, length + definition.getLength());
            Optional<String> formattedString = getFormattedValue(subString.get(), definition.getType());
            if (formattedString.isPresent()) {
                output[index++] = formattedString.get();
            } else {
                String msg = String.format("Record will be dropped. Couldn't parse the value for column %s", definition);
                logger.error(msg);
                return empty();
            }
            length += definition.getLength();
        }

        return Optional.of(join(output, config.getFieldDelimiter()));
    }

    private Optional<String> getFormattedValue(String value, DataType type) {
        if (value == null || value.isEmpty()) {
            return empty();
        }

        switch (type) {
            case DATE:
                return convertDate(value, config.getInputDateFormat(), config.getOutputDateFormat());
            case NUMERIC:
                return isValidNumeric(value) ? Optional.of(value) : empty();
            case STRING:
                return Optional.of(value);
            default:
                return empty();
        }
    }

    private Optional<String> getSubString(String string, int startIndex, int endIndex) {
        if (startIndex > string.length()) {
            return empty();
        }
        endIndex = endIndex > string.length() ? string.length() : endIndex;

        // Remove any additional spaces
        String subString = string.substring(startIndex, endIndex).trim();

        // If the substring already contains the delimiter, then enclose the string in double quotes
        subString = subString.contains(config.getFieldDelimiter()) ? "\"" + subString + "\"" : subString;
        return Optional.of(subString);
    }
}
