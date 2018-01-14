package com.truform.fffc.datatypes;

import com.truform.fffc.exceptions.DateException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Date {
    private String date;

    public Date(String input) throws DateException {
        Pattern p = Pattern.compile("([0-9]{4})-([0-9]{2})-([0-9]{2})");
        Matcher m = p.matcher(input);

        if (!m.matches()) {
            throw new DateException("Ill formatted date, must be yyyy-mm-dd, found: " + input);
        }

        this.date = String.format("%s/%s/%s", m.group(3), m.group(2), m.group(1));
    }

    @Override
    public String toString() {
        return date;
    }
}
