package com.rajashekars.fileformatter.metadata;


import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Metadata {

    public static final int NO_OF_COLUMN_TYPES = 3;
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    private final List<Column> columns = new ArrayList<>();

    public void add(Column column) {
        columns.add(column);
    }

    public List<Column> get() {
        return columns;
    }
}
