/**
 * Copyright 2018 Octo Technologies.
 */
package com.octo.fixedfileformatconverter.impl;

import com.octo.fixedfileformatconverter.Converter;
import com.octo.fixedfileformatconverter.exceptions.InvalidDataFormatException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default Converter.
 *
 * The default converter implementation, converts a raw string to an array of strings using
 * {@link DefaultColumnMetaData}.
 *
 * @author Mark Zsilavecz
 */
public class DefaultConverter implements Converter<DefaultColumnMetaData, String[]>
{

    /**
     * Logging instance.
     */
    public static final Logger LOG = LoggerFactory.getLogger(DefaultConverter.class);
    /**
     * The format that the date string will be converted from.
     */
    private static final DateTimeFormatter FORMAT_BEFORE;
    /**
     * The format that the date string will be converted to.
     */
    private static final DateTimeFormatter FORMAT_AFTER;

    static
    {
        FORMAT_BEFORE = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        FORMAT_AFTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }

    /**
     * Default constructor.
     */
    public DefaultConverter()
    {
    }

    @Override
    public String[] convert(String raw, List<DefaultColumnMetaData> columns) throws InvalidDataFormatException
    {
        LOG.trace("Converting line: {}", raw);
        String[] parts = new String[columns.size()];
        int indexStart = 0;
        for (int i = 0; i < columns.size(); i++)
        {
            DefaultColumnMetaData column = columns.get(i);
            int indexEnd = indexStart + column.getLength();
            if (raw.length() < indexEnd)
            {
                throw new InvalidDataFormatException(String.format("Invalid line length, got %d, expected %d.",
                                                                   raw.length(), columns.stream()
                                                                   .collect(Collectors.summingInt((e) -> e.getLength()))));
            }
            String temp = raw.substring(indexStart, indexEnd);
            switch (column.getFormat())
            {
                case DATE:
                    parts[i] = transformDate(temp);
                    break;
                case NUMERIC:
                    parts[i] = transformNumeric(temp);
                    break;
                case STRING:
                    parts[i] = transformString(temp);
                    break;
                default:
                    break;
            }
            indexStart = indexEnd;
        }
        return parts;
    }

    private String transformDate(String before) throws InvalidDataFormatException
    {
        try
        {
            LocalDate date = LocalDate.parse(before, FORMAT_BEFORE);
            return date.format(FORMAT_AFTER);
        }
        catch (DateTimeParseException e)
        {
            throw new InvalidDataFormatException(String.format("Invalid DATE value: %s", before));
        }
    }

    private String transformString(String before)
    {
        return before.trim();
    }

    private String transformNumeric(String before) throws InvalidDataFormatException
    {
        try
        {
            return Double.valueOf(before).toString();
        }
        catch (NumberFormatException e)
        {
            throw new InvalidDataFormatException(String.format("Invalid NUMERIC value: %s", before));
        }
    }
}
