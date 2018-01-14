package com.truform.fffc.datatypes;

import java.util.List;

public class Metadata {

    private List<Column> columns;

    public Metadata(List<Column> columns) {
        this.columns = columns;
    }

    public int getNumberOfColumns() {
        return columns.size();
    }

    public String getColumnName(int columnIndex) {
        return columns.get(columnIndex).getName();
    }

    public int getColumnWidth(int columnIndex) {
        return columns.get(columnIndex).getWidth();
    }

    public ColumnType getColumnType(int columnIndex) {
        return columns.get(columnIndex).getType();
    }
}
