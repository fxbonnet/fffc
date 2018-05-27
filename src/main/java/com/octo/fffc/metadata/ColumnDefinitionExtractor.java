package com.octo.fffc.metadata;

import com.octo.fffc.exception.InvalidInputException;
import org.springframework.stereotype.Component;

import static com.octo.fffc.metadata.ColumnDefinition.ColumnDefinitionBuilder;

/**
 * The responsibility of the class is to generate {@link ColumnDefinition}
 *
 * @author anoop.shiralige
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class ColumnDefinitionExtractor {

    private static final String FIELD_SEPARATOR = ",";

    /**
     * Parses the given string to generate {@link ColumnDefinition}
     *
     * @param input
     * @return {@link ColumnDefinition}
     * @throws InvalidInputException
     */
    ColumnDefinition extractDefinitions(String input) throws InvalidInputException {
        String[] fields = input.split(FIELD_SEPARATOR);

        //Validate that there are 3 fields at-least
        if (fields.length != 3) {
            throw new InvalidInputException("Expecting atleast 3 fields to indicate the column - name, length, dataType");
        }

        return new ColumnDefinitionBuilder().setName(fields[0])
                .setLength(fields[1])
                .setType(fields[2])
                .build();
    }
}
