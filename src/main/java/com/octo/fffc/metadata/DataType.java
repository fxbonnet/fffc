package com.octo.fffc.metadata;

/**
 * Defines the possible data type definition for metadata file
 *
 * @author anoop.shiralige
 * @version 1.0.0
 * @since 1.0.0
 */
public enum DataType {
    /**
     * Date data type
     */
    DATE("date"),

    /**
     * Double data type
     */
    NUMERIC("numeric"),

    /**
     * String data type
     */
    STRING("string");

    private String name;

    DataType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}