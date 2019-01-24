package com.pg.parser;

/**
 * class to store metadata for the column
 */
public class Column {

    /**
     * name of the column
     */
    private String name;

    /**
     * length of the column
     */
    private Integer length;

    /**
     * type of the column. it and be numeric, string or date
     */
    private String type;

    public Column(String name, Integer length, String type) {
        this.name = name;
        this.length = length;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Column{" +
                "name='" + name + '\'' +
                ", length=" + length +
                ", type='" + type + '\'' +
                '}';
    }
}
