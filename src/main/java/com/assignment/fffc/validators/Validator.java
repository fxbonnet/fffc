package com.assignment.fffc.validators;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class Validator {

    public static int isValidSize(String metadatum) {

        if (!StringUtils.isEmpty(metadatum)) {
            try {
                int size = Integer.parseInt(metadatum);
                if (size > 0) {
                    return size;
                } else {
                    throw new IllegalArgumentException("Column Size Cannot be Zero Or Negative" + size);
                }
            } catch (NumberFormatException ex) {
                throw new NumberFormatException("Specified Column Size is not Numeric " + ex.toString());
            }
        } else {
            throw new IllegalArgumentException("Size of the column is missing  " + metadatum);
        }
    }

    public static boolean isValidString(String... strings) {

        for (String string : strings) {
            if (StringUtils.isEmpty(string)) {
                return false;
            }
        }
        return true;
    }
}
