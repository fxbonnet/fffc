package octo.util;

import octo.exception.MetadataFileException;
import octo.model.ColumnType;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * Created by anjana on 27/05/18.
 */
public class Validator {

    public static void validateMetadataFile(String[] fields) throws MetadataFileException {
        if (fields == null || fields.length != 3) {
            throw new MetadataFileException("Missing column metadata information !! ");
        }
        if (StringUtils.isBlank(fields[0]) || StringUtils.isNumeric(fields[0])) {
            throw new MetadataFileException("Missing column name information !! " + Arrays.asList(fields).toString());
        }
        if (StringUtils.isBlank(fields[1]) || !StringUtils.isNumeric(fields[1])) {
            throw new MetadataFileException("Missing column length information !! " + Arrays.asList(fields).toString());
        }
        if (StringUtils.isBlank(fields[2])) {
            throw new MetadataFileException("Missing column type information !! " + Arrays.asList(fields).toString());
        }
        try {
            ColumnType type = ColumnType.valueOf(fields[2].toUpperCase());
        } catch (Exception e) {
            throw new MetadataFileException("Invalid column type");
        }
    }
}
