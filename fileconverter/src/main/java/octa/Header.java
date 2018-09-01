package octa;

public class Header {
    private String text;
    private int size;
    private String dataType;

    public Header(String text, int size, String dataType) {
        this.text = text;
        this.size = size;
        this.dataType = dataType;
    }

    public String getText() {
        return text;
    }

    public int getSize() {
        return size;
    }

    public String getDataType() {
        return dataType;
    }
}
