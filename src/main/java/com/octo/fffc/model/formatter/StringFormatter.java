package com.octo.fffc.model.formatter;

import com.octo.fffc.exception.ParseErrorException;

import java.util.logging.Level;
import java.util.logging.Logger;

class StringFormatter implements Formatter {

    private static final Logger logger = Logger.getLogger(StringFormatter.class.getName());

    @Override
    public String format(String input) throws ParseErrorException {

        if (input.trim().isEmpty()) {
            logger.log(Level.SEVERE, "String is empty");
            throw new ParseErrorException("[ERROR] String is empty");
        }

        if (input.indexOf(',') >= 0) {
            return "\"" + input.trim() + "\"";
        } else {
            return input.trim();
        }
    }
}
