package com.assignment.fffc.formats;

import com.assignment.fffc.constants.Constants;
import com.assignment.fffc.model.ColumnType;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;

@Component
public class ColumnFormatProvider {

    private final Map<String, Function> formats = Collections.unmodifiableMap(
            new HashMap<String, Function<String, Object>>() {{
                put(ColumnType.STRING.toString(), x -> Pattern.compile(Constants.PUNCTUATIONS).matcher(x.trim()).find() ?
                        ("\"" + x.trim() + "\"") : x.trim());
                put(ColumnType.NUMERIC.toString(), x -> Double.parseDouble(x.trim()));
                put(ColumnType.DATE.toString(), x -> DateTimeFormatter.ofPattern(Constants.DD_MM_YYYY_FORMAT).format(LocalDate.parse(x)));
            }}
    );

    public Function<String, Object> format(String type) {
        return formats.get(type);
    }
}
