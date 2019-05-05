package com.octo.converter.service.util;

import com.octo.converter.service.ConverterException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtil {
    public static String changeDateFormatTo(String inputDate,String inputPattern,String outputPattern) throws ConverterException {
        Date date = null;
        try {
            date = new SimpleDateFormat(inputPattern).parse(inputDate);
        } catch (ParseException e) {
            throw new ConverterException(Constants.ExceptionConstants.UNSUPPORTED_DATE_FORMAT+ inputDate);
        }
        String newstring = new SimpleDateFormat(outputPattern).format(date);
        return newstring;
    }
}
