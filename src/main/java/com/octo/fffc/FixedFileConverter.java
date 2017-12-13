package com.octo.fffc;

import com.octo.fffc.exception.InvalidStructureException;
import com.octo.fffc.exception.ParseErrorException;
import com.octo.fffc.model.ColumnStructure;
import com.octo.fffc.model.formatter.GenericFormatter;
import com.octo.fffc.parser.ColumnStructureParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FixedFileConverter {

    private String metadataFileName;
    private String inputDataFile;

    private static final Logger logger = Logger.getLogger(FixedFileConverter.class.getName());

    public FixedFileConverter(String metadataFileName, String inputDataFile) {
        this.metadataFileName = metadataFileName;
        this.inputDataFile = inputDataFile;
    }

    public void generateCsvFile() {
        List<ColumnStructure> columnStructure = getColumnsStructure();
        doConvertInputFile(columnStructure);
    }

    List<ColumnStructure> getColumnsStructure() {

        List<ColumnStructure> columnStructureList = new ArrayList<>();

        Path filePath = Paths.get(metadataFileName);

        AtomicInteger lineNumber = new AtomicInteger();
        try (Stream<String> stream = Files.lines(filePath)) {

            stream.forEach((String columnDefinitionLine) -> {

                generateColumnDefinitionInfo(columnStructureList, lineNumber.getAndIncrement(), columnDefinitionLine);
            });

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not read metadata file : " + metadataFileName);
            throw new IllegalArgumentException("[ERROR] Could not read metadata file: " + metadataFileName);
        }

        return columnStructureList;

    }

    void doConvertInputFile(List<ColumnStructure> columnStructureList) {

        Path filePath = Paths.get(inputDataFile);
        String outputFileName = inputDataFile.split("\\.")[0] + ".csv";

        File csvFile = new File(outputFileName);

        try (BufferedReader br = Files.newBufferedReader(filePath);
             FileWriter outputFile = new FileWriter(csvFile);
        ) {
            csvFile.createNewFile();
            String line;

            outputFile.write(getHeader(columnStructureList));
            outputFile.write("\n");

            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                String parsedLine = parseLine(lineNumber, line, columnStructureList);
                outputFile.write(parsedLine);
                outputFile.write("\n");
                lineNumber++;
            }

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not read from or write to file");
            throw new IllegalArgumentException("[ERROR] Could not read or write to file");
        }

    }

    String getHeader(List<ColumnStructure> columnStructureList) {

        if (columnStructureList != null && !columnStructureList.isEmpty()) {
            return columnStructureList.stream().map(ColumnStructure::getColumnName).collect(Collectors.joining(","));
        }

        logger.log(Level.SEVERE, "Could not find columns header");
        throw new IllegalArgumentException("[ERROR] Could not find columns header");
    }

    String parseLine(Integer lineNumber, String inputLine, List<ColumnStructure> columnStructureList) {

        StringBuilder parsedLine = new StringBuilder();
        GenericFormatter formatter = new GenericFormatter();

        int startIdx = 0;

        for (ColumnStructure columnStructure : columnStructureList) {

            if (startIdx != 0) {
                parsedLine.append(",");
            }

            Integer length = columnStructure.getLength();

            int endIndex = startIdx + length;
            String substringToParse = inputLine.substring(startIdx, endIndex);

            try {
                parsedLine.append(formatter.format(columnStructure.getType(), substringToParse));

            } catch (ParseErrorException e) {
                logger.log(Level.SEVERE, "Parse error on line: '" + lineNumber + "' of file : " + inputDataFile);
                throw new IllegalArgumentException("[ERROR] Parse error on line: '" + lineNumber + "' of file : " + inputDataFile);
            }

            startIdx = endIndex;
        }

        return parsedLine.toString();

    }

    private void generateColumnDefinitionInfo(List<ColumnStructure> columnStructureList, Integer lineNumber, String columnStructureLine) {
        ColumnStructure columnStructure = null;
        try {
            columnStructure = ColumnStructureParser.parse(columnStructureLine);
            columnStructureList.add(columnStructure);
        } catch (InvalidStructureException e) {
            logger.log(Level.SEVERE, "Could not parse the column structure on line number: " + lineNumber);
            throw new IllegalArgumentException("[ERROR] Could not parse the column structure on line number: " + lineNumber);
        }
    }
}
