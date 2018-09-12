package com.rajashekars.fileformatter.metadata;

public enum DataFormat {
    DATE, NUMERIC, STRING;

    public static DataFormat create(String name) {
        if (name == null) {
            throw new NullPointerException("DataFormat");
        }

        try {
            return valueOf(name.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new InvalidMetadataFormatException("Invalid data format: " + name);
        }
    }
}
