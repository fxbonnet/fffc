package com.truform.fffc.helpers;

import com.truform.fffc.exceptions.DateException;
import com.truform.fffc.exceptions.InvalidLineException;
import com.truform.fffc.exceptions.NumericException;
import com.truform.fffc.datatypes.CSVString;
import com.truform.fffc.datatypes.Date;
import com.truform.fffc.datatypes.Metadata;
import com.truform.fffc.datatypes.Numeric;

public class FixedFileConverter {
    public static String getHeader(Metadata metadata) {
        StringBuilder output = new StringBuilder();
        int col;

        for (col = 0; col < metadata.getNumberOfColumns(); col++) {
            output.append(metadata.getColumnName(col));
            output.append(',');
        }

        if (col > 0)
            return output.deleteCharAt(output.length() - 1).toString();

        return "";
    }

    public static String convertLine(String line, Metadata metadata) throws DateException, NumericException {
        int pos = 0;
        StringBuilder output = new StringBuilder();

        for (int col = 0; col < metadata.getNumberOfColumns(); col++) {
            int newPos = pos + metadata.getColumnWidth(col);

            if (newPos > line.length()) {
                throw new InvalidLineException("Line is shorter than expected; aborting");
            }

            String val = line.substring(pos, pos + metadata.getColumnWidth(col));

            pos = newPos;

            switch (metadata.getColumnType(col)) {
                case DATE:
                    Date date = new Date(val);
                    output.append(date);
                    break;
                case STRING:
                    CSVString string = new CSVString(val);
                    output.append(string);
                    break;
                case NUMERIC:
                    Numeric numeral = new Numeric(val);
                    output.append(numeral);
                    break;
            }
            output.append(',');
        }

        if (pos != line.length()) {
            throw new InvalidLineException("Line is longer than expected; aborting");
        }

        return output.deleteCharAt(output.length() - 1).toString();
    }
}
