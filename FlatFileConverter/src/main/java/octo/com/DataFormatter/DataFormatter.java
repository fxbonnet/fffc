package octo.com.DataFormatter;

import octo.com.exception.InvalidNumberFormatException;
import octo.com.utils.Metadata;

public abstract class DataFormatter {

    String formattedData;

    public DataFormatter(){

    }


    public static DataFormatter create(String line, int index, Metadata.MetadataFields fieldMetaData) throws InvalidNumberFormatException {
        try {
            switch (fieldMetaData.getColumnType()) {
                case NUMERIC:
                    return new NumberFormatter(line, index, fieldMetaData);
                case DATE:
                    return new DateFormatter(line, index, fieldMetaData);
                case STRING:
                    return new StringFormatter(line, index, fieldMetaData);
            }
        }catch(Exception e){
            throw new IllegalArgumentException(fieldMetaData.getColumnType() + "format is not supported!");
        }
        return null;
    }

    public abstract String format();

}
