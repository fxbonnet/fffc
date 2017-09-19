package fffc.entities;

import fffc.Exceptions.InvalidFieldFormatException;

import java.util.regex.Pattern;


/**
 * Represents a Numeric field. Encapsulates numbers specific validation
 */
public class NumericField extends Field{

    private static final Pattern pattern = Pattern.compile("(\\+|-)?([0-9]+(\\.[0-9]+))\n");

    public NumericField(String line, int index, FieldMetaData fieldMetaData) throws InvalidFieldFormatException {
        super(line, index, fieldMetaData);

        if (! value.matches("(\\+|-)?([0-9]+(\\.[0-9]+))")){
            throw new InvalidFieldFormatException(value + " is not a valid numeric value");
        }
//
//        try {
//            Double.valueOf(value);
//        } catch (NumberFormatException e) {
//            throw new InvalidFieldFormatException(value + "is not a valid numeric value");
//        }
    }

    @Override
    public String getFormatted() {
        return value;

    }
}
