package com.trips.ankur.fffc.columns;

import lombok.Data;
import lombok.RequiredArgsConstructor;


/**
 * Defining the columns class.
 * @author tripaank
 *
 */
@Data
@RequiredArgsConstructor
public class Column {
    private final String columnName;
    private final long columnLength;
    private final ColumnType columnType;
}
