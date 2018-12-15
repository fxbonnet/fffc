package com.octoassessment.model;

import java.util.ArrayList;
import java.util.List;

public class Line {
    List<Column> columns = new ArrayList<>();

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    @Override
    public String toString() {
        return "Line{" +
                "columns=" + columns +
                '}';
    }
}
