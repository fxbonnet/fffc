package com.octoassessment.util;

import com.octoassessment.model.*;
import com.octoassessment.transform.Transform;
import com.octoassessment.transform.impl.CommaRemover;
import com.octoassessment.transform.impl.DateFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FormatConvertHelper {

    static Logger logger = Logger.getLogger(FormatConvertHelper.class.getName());
    static List<Transform<String, String, ColumnMetaData>> transformers = Arrays.asList(new CommaRemover(), new DateFormatter());

    public static Line toLine(String input, Metadata metadata) {
        Line line = null;
        int startIndex = 0;
        ColumnMetaData inProgressColMetaData = null;
        if (input != null && metadata != null) {
            try {
                line = new Line();
                for (ColumnMetaData columnMetaData : metadata.getColumnMetaData()) {
                    inProgressColMetaData = columnMetaData;
                    String val = input.substring(startIndex, startIndex + columnMetaData.getColumnLength());
                    if (val != null) {
                        processVal(line, val, input, columnMetaData);
                    } else {
                        logger.log(Level.SEVERE, "Error processing input: [" + input + "] with " + inProgressColMetaData);
                        break;
                    }
                    startIndex += columnMetaData.getColumnLength();
                }
                if (!input.substring(startIndex).trim().isEmpty()) {
                    //some input remained unprocessed
                    logger.log(Level.SEVERE, "Error processing input: [" + input + "] with " + inProgressColMetaData);
                    return null;
                }
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Error processing input: [" + input + "] with " + inProgressColMetaData);
                line = null;
            }
        }
        return line;
    }

    public static void processVal(Line line, String val, String input, ColumnMetaData columnMetaData) {
        if (val != null) {
            val = val.trim();
            for (Transform<String, String, ColumnMetaData> t : transformers) {
                val = t.apply(val, columnMetaData);
            }
            if(columnMetaData.getColumnType() == ColumnType.NUMERIC){
                Double.parseDouble(val); // check its a valid numeric value
            }
            line.getColumns().add(new Column(val));
        } else {
            logger.log(Level.SEVERE, "Error processing input: [" + input + "] with " + columnMetaData);
        }
    }

}
