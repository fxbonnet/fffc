package com.assignment.fffc.processors;

import com.assignment.fffc.constants.Constants;
import com.assignment.fffc.model.Column;
import com.assignment.fffc.validators.Validator;
import com.pivovarit.function.ThrowingFunction;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.List;

import static java.util.stream.Collectors.toList;


@Component
@Qualifier("csvMetadataProcessor")
public class CSVMetadataProcessor implements MetaDataProcessor {


    @Override
    public List<Column> extractMetaData(String metadataFilePath) throws Exception {

        if (Validator.isValidString(metadataFilePath)) {
            return Files.lines(new File(metadataFilePath).toPath()).map(ThrowingFunction.unchecked(
                    this::extractColumnDetails
            )).collect(toList());
        } else {
            throw new IllegalArgumentException("metadataFilePath is null or empty");
        }
    }

    private Column extractColumnDetails(String line) throws ParseException {

        String[] metadata = line.split(Constants.METADATA_SEPARATOR);
        if (metadata.length != Constants.COLUMN_DEFINTION_LENGTH) {
            throw new ParseException("Invalid Column Definition : " + line +
                    "\n This should be of the format <Column-Name>,<Column-Size>,<Column-type>", metadata.length);
        }
        return new Column(metadata[0].trim(), Validator.isValidSize(metadata[1]), metadata[2].trim());

    }

}
