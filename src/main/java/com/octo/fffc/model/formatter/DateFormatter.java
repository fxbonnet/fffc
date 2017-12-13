package com.octo.fffc.model.formatter;

import com.octo.fffc.exception.ParseErrorException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

class DateFormatter implements Formatter {

    private static final Logger logger = Logger.getLogger(DateFormatter.class.getName());

    @Override
    public String format(String dateInput) throws ParseErrorException {

        if (dateInput.trim().isEmpty()) {
            logger.log(Level.SEVERE, "Date is empty");
            throw new ParseErrorException("[ERROR] Date is empty");
        }

        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date date = inputDateFormat.parse(dateInput);
            return outputDateFormat.format(date);
        } catch (ParseException e) {
            logger.log(Level.SEVERE, "Could not parse date: " + dateInput);
            throw new ParseErrorException("[ERROR] Could not parse date: " + dateInput);
        }

    }
}
