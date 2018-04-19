/**
 * Copyright 2018 Octo Technologies.
 */
package com.octo.fixedfileformatconverter;

/**
 * Column Meta Data.
 *
 * Holds the meta data for a single column in the fixed format.
 *
 * @author Mark Zsilavecz
 */
public interface ColumnMetaData
{

    /**
     * @return the name of the column.
     */
    public String getName();

    /**
     * @return the format (type of data) of the column.
     */
    public ColumnFormat getFormat();
}
