package com.assignment.fffc.model;

import lombok.*;

@Getter @Setter @EqualsAndHashCode @ToString
public class Column {

    @NonNull
    private String name;
    @NonNull
    private ColumnType type;
    @NonNull
    private int size;

    public Column(String name, int size, String type) {
        this.name = name;
        this.type = ColumnType.fromString(type.toUpperCase());
        this.size = size;

    }

    public String getType() {
        return type.toString();
    }

    public void setType(String type) {
        this.type = ColumnType.fromString(type);
    }

}
