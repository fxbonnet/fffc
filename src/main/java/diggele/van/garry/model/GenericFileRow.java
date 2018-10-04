package diggele.van.garry.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class GenericFileRow {

    // A row contains a bunch of columns
    private HashMap<String, Column> columns = new HashMap<>();

    public void addColumn(@NotNull Column aColumn) {
        if (columns.containsKey(String.valueOf(aColumn.getPosition()))) {
            throw new IllegalArgumentException("Unable to add a column at an existing position. " + aColumn);
        }
        columns.put(String.valueOf(aColumn.getPosition()), aColumn);
    }

    @Nullable
    public Column getColumnByPosition(int aPosition) {
        return columns.get(String.valueOf(aPosition));
    }

    @Nullable
    public Column getColumnByPosition(@NotNull Column aColumn) {
        return columns.get(String.valueOf(aColumn.getPosition()));
    }

    @Nullable
    public String getColumnValue(@NotNull Column aColumn) {
        Column column = getColumnByPosition(aColumn);
        return (column != null) ? column.getValue() : null;
    }

    public int getNumberOfColumns() {
        return columns.size();
    }

    @Override
    public String toString() {
        return "GenericFileRow{" +
                "columns=" + columns +
                '}';
    }
}