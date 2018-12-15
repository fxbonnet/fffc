package com.octoassessment.model;

import java.util.Objects;

public class ColumnMetaData {

    public ColumnMetaData(String columnName, Integer columnLength, ColumnType columnType) {
        this.columnName = columnName;
        this.columnLength = columnLength;
        this.columnType = columnType;
    }

    private String columnName;
    private Integer columnLength;
    private ColumnType columnType;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Integer getColumnLength() {
        return columnLength;
    }

    public void setColumnLength(Integer columnLength) {
        this.columnLength = columnLength;
    }

    public ColumnType getColumnType() {
        return columnType;
    }

    public void setColumnType(ColumnType columnType) {
        this.columnType = columnType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColumnMetaData that = (ColumnMetaData) o;
        return columnName.equals(that.columnName) &&
                columnLength.equals(that.columnLength) &&
                columnType == that.columnType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(columnName, columnLength, columnType);
    }

    @Override
    public String toString() {
        return "Metadata[" +
                "columnName='" + columnName + '\'' +
                ", columnLength=" + columnLength +
                ", columnType=" + columnType +
                ']';
    }
}
