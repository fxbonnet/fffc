package com.octo.fffc.metadata;

import com.octo.fffc.exception.InvalidInputException;

/**
 * Column level information for each record
 *
 * @author anoop.shiralige
 * @version 1.0.0
 * @since 1.0.0
 */
public class ColumnDefinition {
    private final String name;
    private final int length;
    private final DataType type;

    private ColumnDefinition(String name, int length, DataType type) {
        this.name = name;
        this.length = length;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public DataType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", length=" + length +
                ", type=" + type.getName() +
                '}';
    }

    public static class ColumnDefinitionBuilder {
        private String name;
        private int length;
        private DataType type;

        public ColumnDefinitionBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public ColumnDefinitionBuilder setLength(String length) throws InvalidInputException {
            try {
                this.length = Integer.valueOf(length);
                // make sure that the column width is greater than 0
                if (this.length <= 0) {
                    throw new InvalidInputException("The column length should be greater than 0");
                }
            } catch (NumberFormatException ex) {
                String msg = String.format("The length %s specified is invalid. Please specify a positive integer", length);
                throw new InvalidInputException(msg, ex);
            }
            return this;
        }

        public ColumnDefinitionBuilder setType(String type) throws InvalidInputException {
            try {
                this.type = DataType.valueOf(type.toUpperCase());
            } catch (IllegalArgumentException ex) {
                String msg = String.format("The data type %s provided should belong to date/numeric/string", type);
                throw new InvalidInputException(msg, ex);
            }
            return this;
        }

        public ColumnDefinition build() {
            return new ColumnDefinition(name, length, type);
        }
    }
}