package com.octoassessment.transform.impl;

import com.octoassessment.model.ColumnMetaData;
import com.octoassessment.model.ColumnType;
import com.octoassessment.transform.Transform;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFormatter implements Transform<String,String,ColumnMetaData> {
    static DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    static DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public String apply(String val, ColumnMetaData columnMetaData) {
        assert (columnMetaData != null);

        if (columnMetaData.getColumnType() == ColumnType.DATE) {
            return LocalDate.parse(val, inputFormat).format(outputFormat);
        }
        return val;
    }
}
