package com.octo.fffc.model.metadata;

/**
 * Metadata implementation for fixed file format converter metatdata.
 *
 * @author bharath.raghunathan
 * @version 1.0.0
 * @since 1.0.0
 */
final class FFFCMetadata implements Metadata {

    private final String name;
    private final int startIndex;
    private final int endIndex;
    private final DataType dataType;

    FFFCMetadata(String name, int startIndex, int endIndex, DataType dataType) {
        this.name = name;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.dataType = dataType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getColumnName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getStartIndex() {
        return startIndex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getEndIndex() {
        return endIndex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataType getDataType() {
        return dataType;
    }
}
