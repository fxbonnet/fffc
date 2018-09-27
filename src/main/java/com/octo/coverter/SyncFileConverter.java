package com.octo.coverter;

import com.octo.exception.FixedFileFormatCoverterException;
import com.octo.exception.FixedFileParserException;
import com.octo.parser.metadata.ColumnData;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public interface SyncFileConverter<T> extends FileConverter<T> {

    boolean writeLine(long lineNum, List<T> elements);


    default boolean convert() {

        init();

        File file = new File(getAppConfig().getInputFileName());

        long lineNum = 1;
        try (Scanner scanner = new Scanner(file, StandardCharsets.UTF_8.name())) {

            while (scanner.hasNext()) {
                List<T> elements = parseLine(lineNum, scanner.nextLine());
                writeLine(lineNum, elements);
                lineNum++;
            }

        } catch (FileNotFoundException e) {
            throw new FixedFileFormatCoverterException("Exception parsing metadataFile: " + e.getMessage());
        } catch (FixedFileParserException e) {
            throw new FixedFileFormatCoverterException(String.format("Error at line: %d \n %s", lineNum, e.getMessage()));
        } finally {
            cleanUp();
        }

        return true;
    }


    default List<T> parseLine(long lineNum, String line) {

        int beginIndex = 0;
        int endIndex = 0;
        List<T> elements = new ArrayList<>();

        if (line == null || line.length() == 0) {
            return elements;
        }

        List<ColumnData> columnDataList = getAppConfig().getColumnData();

        boolean exit = false;
        for (ColumnData columnData : columnDataList) {
            beginIndex = endIndex;
            endIndex = beginIndex + columnData.getLength();

            if (line.length() < endIndex) {
                endIndex = line.length() - 1;
                exit = true;
            }

            elements.add(getColumnTypeParserFactory()
                    .getParser(columnData.getColumnType())
                    .parse(line.substring(beginIndex, endIndex)));

            if (exit) {
                break;
            }

        }

        return elements;

    }

}
