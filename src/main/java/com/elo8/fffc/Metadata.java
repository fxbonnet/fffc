package com.elo8.fffc;


public class Metadata {

    public enum DataType {
        date,
        string,
        numeric;
    }

    private final String name;
    private final int length;
    private final DataType type;

    public Metadata(String name, String length, String type) {
        this.name = name;
        this.length = Integer.parseInt(length);
        this.type = DataType.valueOf(type);
    }

    public String getName() {
        return name;
    }

    public Integer getLength() {
        return length;
    }

    public String getType() {
        return type.toString();
    }

    @Override
    public String toString() {
        return "Metadata{" +
                "name='" + name + '\'' +
                ", length='" + length + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
