package diggele.van.garry.model;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GenericFileRepresentation {

    private int numberOfColumns;

    private MetaDataFileDefinition metaDataFileDefinition;

    private List<GenericFileRow> fileRows = new ArrayList<>();

    public GenericFileRepresentation(final MetaDataFileDefinition aMetaDataFileDefinition) {
        numberOfColumns = aMetaDataFileDefinition.getNumberOfColumns();
        metaDataFileDefinition = aMetaDataFileDefinition;
    }

    public void addRow(@NotNull GenericFileRow aGenericFileRow) {
        if (aGenericFileRow.getNumberOfColumns() != getNumberOfColumns()) {
            throw new IllegalArgumentException("Invalid number of columns for the row");
        }
        if (confirmRowMatchesExpected(aGenericFileRow)) {
            fileRows.add(aGenericFileRow);
        } else {
            throw new RuntimeException("Invalid rown, row does not match meta data definition. Row: " + aGenericFileRow
                    + ", Meta Data file definition: " + metaDataFileDefinition);
        }
    }

    private boolean confirmRowMatchesExpected(@NotNull final GenericFileRow aGenericFileRow) {
        if (aGenericFileRow == null) throw new IllegalArgumentException("A generic file row must not be null");
        boolean result = true;
        for (int position = 1; position <= metaDataFileDefinition.getNumberOfColumns(); position++) {
            Column tmpColumn1 = aGenericFileRow.getColumnByPosition(position);
            Column tmpColumn2 = metaDataFileDefinition.getColumnAtPosition(position);
            if ((tmpColumn1 != null) && (tmpColumn2 != null)) {
                result = tmpColumn1.getType().getClass().equals(tmpColumn2.getType().getClass());
            }
            if (!result) {
                break;
            }
        }
        return result;
    }

    public MetaDataFileDefinition getMetaDataFileDefinition() {
        return metaDataFileDefinition;
    }

    public List<GenericFileRow> getFileRows() {
        return fileRows;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    @Override
    public String toString() {
        return "GenericFileRepresentation{" +
                "numberOfColumns=" + numberOfColumns +
                ", metaDataFileDefinition=" + metaDataFileDefinition +
                ", fileRows=" + fileRows +
                '}';
    }
}

