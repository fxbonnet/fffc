package com.an.model;

public class Column {

    private String name;
    private int length;
    private ColumnType columnType;
    private int startPosition;
    private int endPosition;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public ColumnType getColumnType() {
        return columnType;
    }

    public void setColumnType(ColumnType columnType) {
        this.columnType = columnType;
    }

    public int getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(int startPosition) {
        this.startPosition = startPosition;
    }

    public int getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(int endPosition) {
        this.endPosition = endPosition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Column(String name, int length, ColumnType columnType, int startPosition, int endPosition) {
        this.name = name;
        this.length = length;
        this.columnType = columnType;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    @Override
    public String toString() {
        return "Column{" +
                "name='" + name + '\'' +
                ", length=" + length +
                ", columnType=" + columnType +
                ", startPosition=" + startPosition +
                ", endPosition=" + endPosition +
                '}';
    }
}
