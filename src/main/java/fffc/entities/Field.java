package fffc.entities;

import fffc.Exceptions.InvalidFieldFormatException;

public abstract class Field {

    String value;

    public Field(String line, int index, FieldMetaData fieldMetaData) {
        value = line.substring(index , index + fieldMetaData.length).trim();
    }

    public static Field create(String line, int index, FieldMetaData fieldMetaData) throws InvalidFieldFormatException {
        switch (fieldMetaData.getType()){
            case NUMERIC: return new NumericField(line , index, fieldMetaData);
            case DATE: return new DateField(line , index, fieldMetaData);
            case STRING: return new StringField(line , index, fieldMetaData);
        }
        return null;
    }

    public abstract String getFormatted();

}
