package octo.model;

public class ColumnMetadata {

    String name;
    int length;
    ColumnType type;

    public ColumnMetadata(String name, int length, ColumnType type) {
        this.name = name;
        this.length = length;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public ColumnType getType() {
        return type;
    }

    public void setType(ColumnType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ColumnMetadata metadata = (ColumnMetadata) o;

        if (length != metadata.length) return false;
        if (name != null ? !name.equals(metadata.name) : metadata.name != null) return false;
        return type == metadata.type;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + length;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "ColumnMetadata{" +
                "name='" + name + '\'' +
                ", length=" + length +
                ", type=" + type +
                '}';
    }
}
