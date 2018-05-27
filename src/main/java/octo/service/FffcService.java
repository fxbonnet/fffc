package octo.service;

import octo.exception.InputFileException;
import octo.exception.MetadataFileException;
import octo.exception.OutputFileException;
import octo.model.ColumnMetadata;

import java.util.List;

public interface FffcService {

    List<ColumnMetadata> getColumnMetadata(String metadataFileLocation) throws MetadataFileException;
    List<String> readInputFile(String inputFileLocation,List<ColumnMetadata> columnMetadata) throws InputFileException;
    void writeToCsv(String outputFileLocation,List<String> formattedRows) throws OutputFileException;
}
