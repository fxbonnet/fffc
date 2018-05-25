package com.octo.fffc.formatter;

import com.octo.fffc.Configurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static java.util.Optional.empty;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author anoop.shiralige
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class FormatterUtils {

    private static final Logger logger = getLogger(FormatterUtils.class);

    /**
     * Converts the given date from one format to the other
     *
     * @param value      string representation of the date
     * @param fromFormat dateformat in string form. for ex : 'mm/dd/yyyy'
     * @param toFormat   expected dateformat in string form. for ex : 'dd-mm-yyyy'
     * @return
     */
    public static Optional<String> convertDate(String value, String fromFormat, String toFormat) {
        SimpleDateFormat origFormat = new SimpleDateFormat(fromFormat);
        SimpleDateFormat destFormat = new SimpleDateFormat(toFormat);
        try {
            Date date = origFormat.parse(value);
            return Optional.of(destFormat.format(date));
        } catch (ParseException e) {
            logger.error("Couldn't parse the date:{} from {} to {}", value, fromFormat, toFormat);
            logger.debug("", e);
            return empty();
        }
    }

    /**
     * Checks if the given String is a valid number
     *
     * @param value
     * @return true if the value is numeric. Else false.
     */
    public static boolean isValidNumeric(String value) {
        try {
            new BigDecimal(value);
            return true;
        } catch (Exception ex) {
            logger.error("Couldn't parse the numeric value {}", value);
            logger.debug("", ex);
            return false;
        }
    }
}
