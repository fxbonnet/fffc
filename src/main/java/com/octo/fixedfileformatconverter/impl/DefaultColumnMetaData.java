/*
 * Copyright 2018 Octo Techologies.
 */
package com.octo.fixedfileformatconverter.impl;

import com.octo.fixedfileformatconverter.ColumnFormat;
import com.octo.fixedfileformatconverter.ColumnMetaData;
import com.octo.fixedfileformatconverter.exceptions.InvalidColumnFormatException;
import com.octo.fixedfileformatconverter.exceptions.InvalidMetaDataException;
import java.util.Objects;

/**
 * Column Meta Data.
 *
 * The default implementation of {@link ColumnMetaData}.
 *
 * @author Mark Zsilavecz
 */
public class DefaultColumnMetaData implements ColumnMetaData
{

    /**
     * The name of the column.
     */
    private final String name;
    /**
     * The length/width of the column.
     */
    private final int length;
    /**
     * The format of the column data.
     */
    private final ColumnFormat format;

    /**
     * Creates a new instance of {@link DefaultColumnMetaData}.
     *
     * @param name the name of the column.
     * @param length the length, in characters, of the column.
     * @param format the format (type of data) of the column.
     *
     * @return the new {@link DefaultColumnMetaData} instance.
     */
    public static DefaultColumnMetaData from(String name, int length, ColumnFormat format)
    {
        return new DefaultColumnMetaData(name, length, format);
    }

    /**
     * Creates a new instance of {@link DefaultColumnMetaData} from an array of string values.
     *
     * @param values the values, in the order name, length, format.
     *
     * @return the new {@link DefaultColumnMetaData} instance.
     *
     * @throws InvalidMetaDataException if the array does not contain 3, and only 3 values, or the length value is not
     * an integer, or the format value is not a valid format.
     */
    public static DefaultColumnMetaData from(String[] values) throws InvalidMetaDataException
    {
        if (values.length != 3)
        {
            throw new IllegalArgumentException("Expected 3 values for meta data (name, length, format).");
        }
        String name = values[0];
        try
        {
            int length = Integer.valueOf(values[1]);
            ColumnFormat format = ColumnFormat.of(values[2]);

            return new DefaultColumnMetaData(name, length, format);
        }
        catch (NumberFormatException e)
        {
            throw new InvalidMetaDataException("Invalid length value, expected an integer.");
        }
        catch (InvalidColumnFormatException e)
        {
            throw new InvalidMetaDataException(
              String.format("Invalid format value, expected 'date' or 'numeric' or 'string', got '%s'.",
                            e.getMessage()));
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getName()
    {
        return name;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ColumnFormat getFormat()
    {
        return format;
    }

    /**
     * @return the length, in characters, of the column.
     */
    public int getLength()
    {
        return length;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.name);
        hash = 59 * hash + this.length;
        hash = 59 * hash + Objects.hashCode(this.format);
        return hash;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        final DefaultColumnMetaData other = (DefaultColumnMetaData) o;
        return Objects.equals(this.name, other.name)
               && this.length == other.length
               && this.format == other.format;
    }

    @Override
    public String toString()
    {
        return String.format("ColumnMetaData[name=%s, length=%d, format=%s]", name, length, format);
    }

    private DefaultColumnMetaData(String name, int length, ColumnFormat format)
    {
        this.name = name;
        this.length = length;
        this.format = format;
    }

}
