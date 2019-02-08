package com.assignment.fffc.formats;


import com.assignment.fffc.constants.Constants;
import com.assignment.fffc.model.Column;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class HeaderFormatProvider {

    private final Map<String, Function<List<Column>, String>> formats = Collections.unmodifiableMap(
            new HashMap<String, Function<List<Column>, String>>() {{
                put("csv", columns -> {
                    return columns.stream()
                            .map(column -> column.getName())
                            .collect(Collectors.joining(Constants.CSV_SEPARATOR)) + Constants.LINE_SEPARATOR;
                });
            }}
    );

    public String addHeader(String formatType, List<Column> columnList) {

        if (formats.containsKey(formatType)) {
            return formats.get(formatType).apply(columnList);
        } else {
            throw new IllegalArgumentException("No format-type defined as: " + formatType + " Supported Format-Type are:"
                    + formats.keySet().stream().collect(Collectors.joining(Constants.CSV_SEPARATOR)));
        }

    }
}
