package fffc.entities;

import fffc.Exceptions.InvalidFieldFormatException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Represents a Date field. Encapsulates date specific validation and output formatting
 */

public class DateField extends Field{

    private static SimpleDateFormat sourceSdf = new SimpleDateFormat("yyyy-mm-dd");
    private static SimpleDateFormat destSdf = new SimpleDateFormat("dd/mm/yyyy");


    public DateField(String line, int index, FieldMetaData fieldMetaData) throws InvalidFieldFormatException {
        super(line, index, fieldMetaData);
        try {
            sourceSdf.parse(value);
        } catch (ParseException ex) {
            throw new InvalidFieldFormatException(value + " is not a valid date value");
        }
    }

    @Override
    public String getFormatted() {

        try {
            return destSdf.format(sourceSdf.parse(value));
        }
        catch (ParseException e) {/* won't happen */}

        return null;
    }
}
