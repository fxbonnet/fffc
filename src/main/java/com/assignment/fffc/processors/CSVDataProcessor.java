package com.assignment.fffc.processors;

import com.assignment.fffc.constants.Constants;
import com.assignment.fffc.formats.ColumnFormatProvider;
import com.assignment.fffc.model.Column;
import com.assignment.fffc.validators.Validator;
import com.pivovarit.function.ThrowingFunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Qualifier("csvDataProcessor")
@Slf4j
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CSVDataProcessor implements DataProcessor {

    private ColumnFormatProvider columnFormatter;
    private int beginIndex = 0;

    @Autowired
    public CSVDataProcessor(ColumnFormatProvider columnFormatter) {
        this.columnFormatter = columnFormatter;
    }

    @Override
    public String process(String line, List<Column> columns) {

            if (Validator.isValidString(line) && !CollectionUtils.isEmpty(columns)) {
                return columns.stream()
                        .map(ThrowingFunction.unchecked(columnType ->
                                columnFormatter.format(columnType.
                                        getType()).apply(computeString(line, columnType)).toString()
                        )).collect(Collectors.joining(Constants.CSV_SEPARATOR)) + Constants.LINE_SEPARATOR;
            } else {
                throw new IllegalArgumentException("empty line in Data file Or No Column Specified in the metaDataFile");
            }

    }

    private String computeString(String unFormattedString, Column column) throws Exception {
        try {
            String computed = unFormattedString.substring(beginIndex, beginIndex + column.getSize());
            beginIndex = beginIndex + column.getSize() >= unFormattedString.length() ? 0 :
                    beginIndex + column.getSize();
            return computed;
        } catch (Exception ex) {
            throw new Exception("Format of the line: " + unFormattedString + " in the datafile" +
                    " does not match the column sizes specified in the metadata file " + ex.toString());
        }
    }

}
