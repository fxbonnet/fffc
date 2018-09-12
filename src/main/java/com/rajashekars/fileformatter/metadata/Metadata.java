package com.rajashekars.fileformatter.metadata;


import java.util.ArrayList;
import java.util.List;

public class Metadata {

    private final List<Column> metadata = new ArrayList<>();

    public void add(Column column) {
        metadata.add(column);
    }

    public List<Column> get() {
        return metadata;
    }
}
