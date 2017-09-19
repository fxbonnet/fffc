package fffc.entities;

import fffc.Exceptions.InvalidFieldFormatException;

import java.util.regex.Pattern;

/**
 * Represents a String field with. Encapsulates string specific validation and output formatting
 */
public class StringField extends Field{

    private static final Pattern pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);

    public StringField(String line, int index, FieldMetaData fieldMetaData) throws InvalidFieldFormatException {
        super(line, index, fieldMetaData);

    }

    @Override
    public String getFormatted() {

        if (pattern.matcher(value).find()){
            return "\"" + value + "\"";
        }
        return value;
    }
}
