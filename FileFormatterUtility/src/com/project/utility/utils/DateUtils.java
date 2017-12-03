package com.project.utility.utils;

import com.project.utility.exception.IncorrectDataFormatException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * This class provides Date util methods to handle date conversions.
 * Created by rssridh on 12/3/17.
 */
public class DateUtils {
    final static String YEAR_TO_DATE = "yyyy-MM-dd";
    final static String DATE_TO_YEAR = "dd-MM-yyyy";

    public static String convertDateToYearFormat(String dateString) throws IncorrectDataFormatException {
        try {
            LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern(YEAR_TO_DATE));
            return date.format(DateTimeFormatter.ofPattern(DATE_TO_YEAR));
        } catch (Exception e) {
            throw new IncorrectDataFormatException("Incorrect date format in the input file" + e.getMessage());
        }
    }
}
