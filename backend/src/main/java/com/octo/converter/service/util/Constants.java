package com.octo.converter.service.util;

public class Constants {
    public static class ExceptionConstants{
        public static String INSUFFICIENT_FILES="Please select two files - Fixed format file and csv metadata file";
        public static String NO_METADATA_FOUND="Please attach metadata csv file";
        public static String EMPTY_FILE="File shouldn't be empty ";
        public static String UNSUPPORTED_CHAR="Unsupported character in string ";
        public static String UNSUPPORTED_NUMBER_FORMAT="Unsupported number format";
        public static String UNSUPPORTED_COLUMN_TYPE="Unsupported column type:";
        public static String UNSUPPORTED_DATE_FORMAT="Unsupported date format";
        public static String CRLF_NOT_ALLOWED="CR and LF not allowed inside double quotes - at row:";
        public static String VALUE_EXCEEDED_LENGTH="String inside double quotes exceeded the length - at row:";
        public static String MISSING_COLUMN="Missing cell value at row:";
        public static String SPECIAL_CHAR_NOT_ALLOWED="No special character allowed for string not enclosed in double quotes :";
    }
    public static String CSV_EXTENSION="csv";
    public static final String DATE_TYPE="date";
    public static final String STRING_TYPE="string";
    public static final String NUMERIC_TYPE="numeric";

    public static final String dot=".";
    public static final char space=' ';
}
