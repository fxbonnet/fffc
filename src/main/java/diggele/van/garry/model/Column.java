package diggele.van.garry.model;

import com.google.common.base.Preconditions;
import diggele.van.garry.model.type.ColumnTypeFactory;
import diggele.van.garry.model.type.TypeConverter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Column {

    private String name;

    private int position;

    private TypeConverter type;

    private int length;

    private String value;

    private Column(@NotNull final String aName, final int aPosition, @NotNull final TypeConverter aType, final int aLength, final String aValue) {
        name = aName;
        position = aPosition;
        type = aType;
        length = aLength;
        setValue(aValue);
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public TypeConverter getType() {
        return type;
    }

    public int getLength() {
        return length;
    }

    public String getValue() {
        return (value != null) ? getType().convert(value) : null;
    }

    public void setValue(final String aValue) {
        // Test value if it is a valid value for the associated type.
        if (aValue != null) {
            type.convert(aValue);
        }
        value = aValue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getPosition(), getType(), getLength(), getValue());
    }

    @Override
    public boolean equals(final Object aO) {
        if (this == aO) return true;
        if (!(aO instanceof Column)) return false;
        Column column = (Column) aO;
        return getPosition() == column.getPosition() &&
                getLength() == column.getLength() &&
                Objects.equals(getName(), column.getName()) &&
                Objects.equals(getType(), column.getType()) &&
                Objects.equals(getValue(), column.getValue());
    }

    @Override
    public String toString() {
        return "Column{" +
                "name='" + name + '\'' +
                ", position=" + position +
                ", type=" + type +
                ", length=" + length +
                ", value='" + value + '\'' +
                '}';
    }

    public static class ColumnBuilder {
        private String name;

        private int position;

        private String type;

        private int length;

        private String value;

        public Column createColumn() throws IllegalAccessException, InstantiationException {
            return new Column(name, position, ColumnTypeFactory.getNewInstanceType(type), length, value);
        }

        public Column createColumnClone(@NotNull Column aColumn) {
            return new Column(
                    aColumn.name,
                    aColumn.position,
                    aColumn.type,
                    aColumn.length,
                    aColumn.value
            );
        }

        public ColumnBuilder name(String aName) {
            name = aName.trim();
            return this;
        }

        public ColumnBuilder position(int aPosition) {
            position = aPosition;
            return this;
        }

        public ColumnBuilder type(String aType) {
            Preconditions.checkArgument(ColumnTypeFactory.checkTypeRegistry(aType));
            type = aType.trim();
            return this;
        }

        public ColumnBuilder length(int aLength) {
            length = aLength;
            return this;
        }

        public ColumnBuilder value(String aValue) {
            value = aValue.trim();
            return this;
        }
    }
}
