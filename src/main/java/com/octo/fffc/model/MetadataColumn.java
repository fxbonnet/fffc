package com.octo.fffc.model;

/**
 * @author alanterriaga
 * @project FFFC
 */
public class MetadataColumn {

    private static final String DATE = "date";
    private static final String TEXT = "string";
    private static final String NUMERIC = "numeric";

    private String header;
    private int size;
    private ColumnFormat columnFormat;

    public MetadataColumn(String header, int size, String format){
        this.header = header;
        this.size = size;
        this.columnFormat = fromString(format);
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public ColumnFormat getColumnFormat() {
        return columnFormat;
    }

    public void setColumnFormat(ColumnFormat columnFormat) {
        this.columnFormat = columnFormat;
    }

    public enum ColumnFormat {
        TEXT,
        DATE,
        NUMERIC
    }

    private static ColumnFormat fromString(String value){
        switch (value.trim()){
            case "numeric":
                return ColumnFormat.NUMERIC;
            case "date":
                return ColumnFormat.DATE;
            default:
                return ColumnFormat.TEXT;
        }
    }
}
