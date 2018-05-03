package octo.com.DataFormatter;

import octo.com.exception.InvalidDateFormatException;
import octo.com.utils.Metadata;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents a Date field. Encapsulates date specific validation and output formatting
 */

public class DateFormatter extends DataFormatter {

    private final SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-mm-dd");
    private final SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/mm/yyyy");
    private Date parsedDate;


    public DateFormatter(String line, int index, Metadata.MetadataFields fieldMetaData) throws InvalidDateFormatException
    {

        String formattedData = line.substring(index , index + fieldMetaData.columnSize).trim();
        try {
            parsedDate = inputDateFormat.parse(formattedData);
        } catch (ParseException exception) {
            throw new InvalidDateFormatException("Invalid Date Format"+formattedData);
                //exception.printStackTrace();
                //System.out.println("Invalid Date Format"+formattedData);
        }
    }

    @Override
    public String format()
    {
        return outputDateFormat.format(parsedDate);
    }
}
