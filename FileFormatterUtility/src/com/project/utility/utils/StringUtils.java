package com.project.utility.utils;

import com.project.utility.exception.IncorrectDataFormatException;


/**
 * Created by rssridh on 12/4/17.
 */
public class StringUtils {

    public static String converFileExtensions(final String fileName, final String fileExtension) {

        int index = fileName.lastIndexOf('.');
        String fileNameWithoutExtension = fileName;
        if (index > 0) {
            System.out.println("The file extension was converted to a " + fileExtension + " file");
            fileNameWithoutExtension = fileName.substring(0, index);
        }

        return fileNameWithoutExtension + "." + fileExtension;
    }

    public static String formatString(final String text) {
        if (text.contains(",")) {
            return "\"" + text + "\"";
        }
        return text;
    }
}
