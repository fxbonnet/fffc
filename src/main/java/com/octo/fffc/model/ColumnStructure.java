package com.octo.fffc.model;

public class ColumnStructure {

    private String columnName;
    private Integer length;
    private ColumnType type;

    public ColumnStructure(String columnName, Integer length, ColumnType type) {
        this.columnName = columnName;
        this.length = length;
        this.type = type;
    }

    public String getColumnName() {
        return columnName;
    }

    public Integer getLength() {
        return length;
    }

    public ColumnType getType() {
        return type;
    }
}
