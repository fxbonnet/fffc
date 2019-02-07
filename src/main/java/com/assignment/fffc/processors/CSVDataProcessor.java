package com.assignment.fffc.processors;

import com.assignment.fffc.constants.Constants;
import com.assignment.fffc.formats.ColumnFormatProvider;
import com.assignment.fffc.model.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Qualifier("csvDataProcessor")
public class CSVDataProcessor implements DataProcessor {

    private ColumnFormatProvider columnFormatter;

    private int beginIndex = 0;

    @Autowired
    public CSVDataProcessor(ColumnFormatProvider columnFormatter) {
        this.columnFormatter = columnFormatter;
    }

    @Override
    public String process(String line, List<Column> columns) {

        return columns.stream()
                .map(columnType ->
                        columnFormatter.format(columnType.
                                getType()).apply(computeString(line, columnType)).toString()
                )
                .collect(Collectors.joining(Constants.CSV_SEPARATOR));

    }

    private String computeString(String parseMe, Column column) {

        String computed = parseMe.substring(beginIndex, beginIndex + column.getSize());
        if (beginIndex + column.getSize() >= parseMe.length()) {
            beginIndex = 0;
        } else {
            beginIndex += column.getSize();
        }
        return computed;
    }

}
