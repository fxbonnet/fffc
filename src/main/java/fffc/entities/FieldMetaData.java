package fffc.entities;

import fffc.enums.Type;

/**
 *  Represents meta data for a single field
 */

public class FieldMetaData {
    private String title;
    private Type type;
    int length;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
