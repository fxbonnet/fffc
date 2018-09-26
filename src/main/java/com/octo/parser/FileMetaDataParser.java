package com.octo.parser;

import com.octo.exception.FixedFileFormatCoverterException;
import com.octo.parser.metadata.ColumnData;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileMetaDataParser {
    public List<ColumnData> parse(String inputFormatFileName) {

        File file = new File(inputFormatFileName);
        List<ColumnData> columnDataList = new ArrayList<>();

        try (Scanner scanner = new Scanner(file, StandardCharsets.UTF_8.name())) {

            while (scanner.hasNext()) {
                String[] params = scanner.nextLine().split(",");
                columnDataList.add(new ColumnData(params));
            }

        } catch (FileNotFoundException e) {
            throw new FixedFileFormatCoverterException("Exception parsing metadataFile: " + e.getMessage());
        }


        return columnDataList;
    }
}
