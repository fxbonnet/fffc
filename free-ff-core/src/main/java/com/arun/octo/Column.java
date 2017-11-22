package com.arun.octo;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Column {
    private final String columnName;
    private final long columnLength;
    private final ColumnType columnType;
}
