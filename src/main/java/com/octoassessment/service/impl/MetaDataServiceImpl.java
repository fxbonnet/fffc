package com.octoassessment.service.impl;

import com.octoassessment.exception.FixedFileFormatConversionException;
import com.octoassessment.model.ColumnMetaData;
import com.octoassessment.model.ColumnType;
import com.octoassessment.model.Metadata;
import com.octoassessment.service.MetaDataService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class MetaDataServiceImpl implements MetaDataService<Metadata> {

    @Override
    public Metadata readMetadata(String filePath) throws FixedFileFormatConversionException {
        Metadata metadata;
        try {
            metadata = new Metadata();
            metadata.setColumnMetaData(Files.lines(Paths.get(filePath)).map(t -> t.split(","))
                    .map(v -> getColumnMetaData(v)).collect(Collectors.toList()));
        } catch (IOException e) {
            throw new FixedFileFormatConversionException("Error reading metadata file");
        }
        return metadata;
    }

    private ColumnMetaData getColumnMetaData(String[] colMetaData) {
        assert(colMetaData != null);
        return new ColumnMetaData(colMetaData[0], Integer.parseInt(colMetaData[1]), ColumnType.valueOf(colMetaData[2].toUpperCase()));
    }
}
