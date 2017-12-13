package com.octo.fffc.model.formatter;

import com.octo.fffc.exception.ParseErrorException;

import java.util.logging.Level;
import java.util.logging.Logger;

class NumericFormatter implements Formatter {

    private static final Logger logger = Logger.getLogger(DateFormatter.class.getName());

    @Override
    public String format(String input) throws ParseErrorException {

        if (input.trim().isEmpty()) {
            logger.log(Level.SEVERE, "Numeric value is not defined");
            throw new ParseErrorException("[ERROR] Numeric value is not defined");
        }

        try {
            return Double.valueOf(input.trim()).toString();

        } catch (NumberFormatException e) {
            logger.log(Level.SEVERE, "Could not parse column as a numeric value:" + input);
            throw new ParseErrorException("[ERROR] Could not parse column as a numeric value: " + input);
        }

    }
}
