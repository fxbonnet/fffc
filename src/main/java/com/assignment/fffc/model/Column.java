package com.assignment.fffc.model;

import java.util.Objects;

public class Column {

    private String name;
    private ColumnType type;
    private int size;

    public Column(String name, int size, String type) {
        this.name = name;
        this.type = ColumnType.fromString(type.toUpperCase());
        this.size = size;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type.toString();
    }

    public void setType(String type) {
        this.type = ColumnType.fromString(type);
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Column that = (Column) o;
        return size == that.size &&
                Objects.equals(name, that.name) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, type, size);
    }

    @Override
    public String toString() {
        return "Column{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", size=" + size +
                '}';
    }
}
