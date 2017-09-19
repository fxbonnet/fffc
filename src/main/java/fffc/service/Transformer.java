package fffc.service;

import fffc.Exceptions.InvalidFieldFormatException;
import fffc.Exceptions.InvalidLineFormatException;
import fffc.entities.Field;
import fffc.entities.FieldMetaData;
import fffc.entities.MetaData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  Responsible for transforming one line of input file to one line of CSV based on meta data
 */
public class Transformer {

    public static String transform(String line, MetaData metaData) throws InvalidFieldFormatException, InvalidLineFormatException {

        if (line.length() < metaData.expectedLength()) {
            throw new InvalidLineFormatException("Not enough characters in the line of data");
        }

        List<Field> fields = new ArrayList<>();
        int index =0;

        for (FieldMetaData md: metaData.getMetaData()) {
            fields.add(Field.create(line, index, md));
            index += md.getLength();
        }

        return fields.stream().map(Field::getFormatted).collect( Collectors.joining( "," ) );
    }
}
