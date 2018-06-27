package test.file.model;

import java.io.Serializable;

public class MetaData implements Serializable {

    private static final long serialVersionUID = -2698125104894396375L;
    private String header;
    private int length;
    private String type;

    public MetaData() {
    }

    public MetaData(String header, int length, String type) {
        this.header = header;
        this.length = length;
        this.type = type;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MetaData metaData = (MetaData) o;

        if (length != metaData.length) return false;
        if (header != null ? !header.equals(metaData.header) : metaData.header != null) return false;
        return type != null ? type.equals(metaData.type) : metaData.type == null;

    }

    @Override
    public int hashCode() {
        int result = header != null ? header.hashCode() : 0;
        result = 31 * result + length;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
