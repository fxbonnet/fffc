package com.octo.fffc.model.metadata;

/**
 * Class that represents metadata of each column.
 *
 * @author bharath.raghunathan
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Metadata {

    /**
     * Get the column name of the column this metadata represents.
     *
     * @return Column name
     */
    String getColumnName();

    /**
     * Get the start index from where the column value can be read.
     *
     * @return An integer value that represents the starting position where the data can be read.
     */
    int getStartIndex();

    /**
     * Get the end index upto which the column value can be read.
     *
     * @return An integer value that represents the end position util which the data can be read.
     */
    int getEndIndex();


    /**
     * DataType of the column this metadata represents
     * @return - {@link DataType}
     */
    DataType getDataType();

}
