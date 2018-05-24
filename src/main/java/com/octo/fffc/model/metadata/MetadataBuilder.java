package com.octo.fffc.model.metadata;

import com.octo.fffc.exceptions.MetadataBuilderException;

import static com.octo.fffc.model.metadata.DataType.*;
import static java.lang.Integer.parseInt;
import static org.apache.commons.lang3.StringUtils.isNumeric;

/**
 * Builder class responsible for building a metadata from a given input.
 *
 * @author bharath.raghunathan
 * @version 1.0.0
 * @since 1.0.0
 */
public class MetadataBuilder {

    private static final String DATE_TYPE = "date";
    private static final String NUMERIC_TYPE = "numeric";

    private MetadataBuilder() {
    }

    public static Metadata build(String input, Metadata previousMetadata) {
        String[] fields = getFields(input);

        validateFields(fields);

        int length = parseInt(fields[1]);
        int startIndex = previousMetadata == null ? 0 : previousMetadata.getEndIndex();
        return new FFFCMetadata(fields[0],
                startIndex,
                startIndex + length,
                getDataType(fields[2]));
    }

    private static String[] getFields(String input) {
        return input.split(",");
    }

    private static void validateFields(String[] fields) {

        //field[0] - Column name
        //field[1] - Column length
        //field[2] - data type.

        if (fields == null || fields.length != 3) {
            throw new MetadataBuilderException("Invalid number of records for metadata. Metadata should contain 3 fields");
        }

        if (!isNumeric(fields[1])) {
            throw new MetadataBuilderException("Cannot convert length field to number");
        }

        if (!(fields[2].equalsIgnoreCase(DATE.name()) ||
                fields[2].equalsIgnoreCase(NUMERIC.name()) ||
                fields[2].equalsIgnoreCase(STRING.name()))) {
            throw new MetadataBuilderException("Invalid Data type " + fields[2]);
        }
    }

    private static DataType getDataType(String dataType) {

        if (dataType.equalsIgnoreCase(DATE_TYPE)) {
            return DATE;
        }
        if (dataType.equalsIgnoreCase(NUMERIC_TYPE)) {
            return NUMERIC;
        } else {
            return STRING;
        }
    }
}
