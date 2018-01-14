package com.truform.fffc.helpers;

import com.truform.fffc.datatypes.ColumnType;
import com.truform.fffc.exceptions.MetadataException;
import com.truform.fffc.datatypes.Column;
import com.truform.fffc.datatypes.Metadata;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class MetadataReader {

    public static Metadata readMetadata(Path file) {
        List<String> plainLines;

        try {
            plainLines = Files.readAllLines(file);
        } catch (IOException e) {
            throw new MetadataException("Error reading Metadata File '" + file.toString() + "'", e);
        }
        ArrayList<Column> columnArrayList = new ArrayList<>();

        for (String line: plainLines) {
            addColumnFromLine(columnArrayList, line);
        }

        return new Metadata(columnArrayList);
    }

    static void addColumnFromLine(ArrayList<Column> columnArrayList, String line) {
        String[] parts = line.split(",");

        if (parts.length != 3) {
            throw new MetadataException("Metadata line has the wrong number of columns: " + line);
        }

        String name = parts[0];

        if (!parts[1].matches("[0-9]+")) {
            throw new MetadataException("Metadata width field ill-formatted: '" + parts[1] + "' is not a positive integer");
        }
        int width = Integer.parseInt(parts[1]);

        if (!parts[2].matches("(date|string|numeric)")) {
            throw new MetadataException("Metadata type must be one of 'date', 'string', 'numeric'; found " + parts[2]);
        }
        ColumnType columnType = ColumnType.valueOf(parts[2].toUpperCase());

        columnArrayList.add(new Column(name, width, columnType));
    }
}
