package octo.com.service;

import octo.com.DataFormatter.DataFormatter;
import octo.com.exception.InvalidFileFormatException;
import octo.com.exception.InvalidNumberFormatException;
import octo.com.utils.Metadata;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class FileConverterService {

    public String convertFile(String line, Metadata metaData) throws InvalidNumberFormatException, InvalidFileFormatException {

        List<DataFormatter> data = new ArrayList<>();
        int index =0;

        for (Metadata.MetadataFields mf: metaData.getMetaData()) {
            data.add(DataFormatter.create(line, index, mf));
            index += mf.getColumnSize();
        }

        return data.stream().map(DataFormatter::format).collect( Collectors.joining( "," ) );
    }

}
