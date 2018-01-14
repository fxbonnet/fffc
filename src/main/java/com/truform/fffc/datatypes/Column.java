package com.truform.fffc.datatypes;

public class Column {
    private String name;
    private int width;
    private ColumnType type;

    public Column(String name, int width, ColumnType type) {
        this.name = name;
        this.width = width;
        this.type = type;
    }

    String getName() {
        return name;
    }

    int getWidth() {
        return width;
    }

    ColumnType getType() {
        return type;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass())
            return false;

        Column that = (Column)obj;

        return this.name.equals(that.name) &&
                this.type.equals(that.type) &&
                this.width==that.width;
    }
}
