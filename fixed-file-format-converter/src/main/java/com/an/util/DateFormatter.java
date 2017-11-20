package com.an.util;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;

public class DateFormatter {

    public static  String formatDate(String sourceDate) throws Exception{

        //check if source date is null
        if(sourceDate != null) sourceDate = sourceDate.trim();
        //if source date is empty or null, return original value
        if(StringUtils.isEmpty(sourceDate)) return sourceDate;

        //if source date is not empty, try to format it
        SimpleDateFormat sourceDateFormat = new SimpleDateFormat("yyyy-mm-dd");
        SimpleDateFormat destDateFormat = new SimpleDateFormat("dd/mm/yyyy");

        sourceDate = destDateFormat.format(sourceDateFormat.parse(sourceDate));

        return sourceDate;
    }
}
