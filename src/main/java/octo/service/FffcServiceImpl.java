package octo.service;

import octo.exception.InputFileException;
import octo.exception.MetadataFileException;
import octo.exception.OutputFileException;
import octo.model.ColumnMetadata;
import octo.model.ColumnType;
import octo.util.DataFormatter;
import octo.util.Validator;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class FffcServiceImpl implements FffcService {

    public static final Logger LOG = Logger.getLogger(FffcServiceImpl.class);
    public static final String METADATA_SEPARATOR = ",";
    public static final String CSV_FIELD_SEPARATOR = ",";

    public List<ColumnMetadata> getColumnMetadata(String metadataFileLocation) throws MetadataFileException {
        LOG.info("Reading the column metadata file from " + metadataFileLocation);

        List<ColumnMetadata> columnMetadataList = new ArrayList<>();
        try {
            BufferedReader metadataBuffer = Files.newBufferedReader(Paths.get(metadataFileLocation), StandardCharsets.UTF_8);
            String line;
            while ((line = metadataBuffer.readLine()) != null) {
                columnMetadataList.add(parseColumnMetadata(line));
            }
        } catch (IOException e) {
            LOG.error("Problem parsing column metadata file !!! " + e.getMessage());
            throw new MetadataFileException("Problem parsing column metadata file !!! " + e.getMessage(), e);
        }
        return columnMetadataList;
    }

    private ColumnMetadata parseColumnMetadata(String metadata) throws MetadataFileException {
        LOG.trace("Metadata Fields: " + metadata);
        String[] fields = metadata.split(METADATA_SEPARATOR);
        Validator.validateMetadataFile(fields);
        String columnName = fields[0].trim();
        int columnLength = Integer.parseInt(fields[1]);
        ColumnType columnType = ColumnType.valueOf(fields[2].toUpperCase());
        return new ColumnMetadata(columnName, columnLength, columnType);
    }

    public List<String> readInputFile(String inputFileLocation, List<ColumnMetadata> columnMetadataList) throws InputFileException {
        LOG.info("Parsing the input file at " + inputFileLocation + " based on given metadata");
        List<String> formattedRows = new ArrayList<>();
        formattedRows.add(getColumnNamesHeader(columnMetadataList));
        try {
            BufferedReader inputFileBuffer = Files.newBufferedReader(Paths.get(inputFileLocation));
            String line;
            while ((line = inputFileBuffer.readLine()) != null) {
                formattedRows.add(getFormattedRow(line, columnMetadataList));
            }
        } catch (IOException e) {
            LOG.error("Problem parsing input file !!! " + e.getMessage());
            throw new InputFileException("Problem parsing input file !!! " + e.getMessage(), e);
        } catch (Exception e) {
            throw new InputFileException("Problem parsing input file !!! " + e.getMessage(), e);
        }

        return formattedRows;
    }

    private String getFormattedRow(String inputRow, List<ColumnMetadata> columnMetadataList) {
        LOG.debug("Input Row:: " + inputRow);
        StringBuffer formattedRow = new StringBuffer();
        for (ColumnMetadata metadata : columnMetadataList) {
            String field = inputRow.substring(0, metadata.getLength());
            String formattedField = DataFormatter.formatData(field, metadata.getType());
            if (formattedRow.length() != 0) {
                formattedRow.append(CSV_FIELD_SEPARATOR);
            }
            formattedRow.append(formattedField);
            inputRow = inputRow.substring(metadata.getLength());
        }
        LOG.debug("Formatted Row:: " + formattedRow.toString());
        return formattedRow.toString();
    }

    private String getColumnNamesHeader(List<ColumnMetadata> columnMetadataList) {
        StringBuffer columnNameHeader = new StringBuffer();
        for (ColumnMetadata columnMetadata : columnMetadataList) {
            if (columnNameHeader.length() != 0) {
                columnNameHeader.append(CSV_FIELD_SEPARATOR);
            }
            columnNameHeader.append(columnMetadata.getName());
        }
        return columnNameHeader.toString();
    }

    public void writeToCsv(String csvFileLocation, List<String> formattedRows) throws OutputFileException {
        LOG.info("Writing to the csv file:  " + csvFileLocation);
        Path path = Paths.get(csvFileLocation);
        try {
            Files.write(path, formattedRows, StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOG.error("Problem creating output file !!! " + e.getMessage());
            throw new OutputFileException("Problem creating output file !!! " + e.getMessage(), e);
        }

    }
}
