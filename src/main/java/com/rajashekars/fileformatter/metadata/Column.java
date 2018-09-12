package com.rajashekars.fileformatter.metadata;

public class Column {

    private String name;
    private int length;
    private DataFormat type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public DataFormat getType() {
        return type;
    }

    public void setType(DataFormat type) {
        this.type = type;
    }
}
