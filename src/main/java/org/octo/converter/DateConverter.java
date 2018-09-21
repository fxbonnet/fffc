package org.octo.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {

    private static Logger logger = LoggerFactory.getLogger(DateConverter.class);
    public static DateFormat srcFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static DateFormat dstFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static Object[] convertDataTypes(Object[] records, int[] index) throws Exception {
        try {
            Date date;
            for (int indx : index) {
                date = srcFormat.parse((String) records[indx]);
                logger.trace("Converting date value :" + records[indx]);
                records[indx] = dstFormat.format(date);
                logger.trace("Converted date value :" + records[indx]);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            logger.error("Date format parsing exception occurred " + e.getMessage());
            throw e;
        }
        return records;
    }
}
