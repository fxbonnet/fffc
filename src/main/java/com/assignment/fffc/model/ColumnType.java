package com.assignment.fffc.model;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ColumnType {
    STRING, NUMERIC, DATE;

    private static final Map<String, ColumnType> NAME_MAP = Stream.of(values())
            .collect(Collectors.toMap(ColumnType::toString, Function.identity()));

    public static ColumnType fromString(final String name) {
        ColumnType myEnum = NAME_MAP.get(name.toUpperCase());
        if (null == myEnum) {
            throw new IllegalArgumentException(String.format("'%s' is not a valid columnType. Accepted values: %s", name, Arrays.asList(values())));
        }
        return myEnum;
    }
}
