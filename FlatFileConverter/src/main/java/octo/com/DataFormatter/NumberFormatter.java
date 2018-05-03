package octo.com.DataFormatter;

import octo.com.exception.InvalidNumberFormatException;
import octo.com.utils.Metadata;

import java.util.regex.Pattern;

public class NumberFormatter extends DataFormatter {


    String formattedData;

    public NumberFormatter(String line, int index, Metadata.MetadataFields fieldMetaData)  {
        int numberIndex = index + fieldMetaData.columnSize;
        if( line.length() < numberIndex ){
            numberIndex = line.length();
        }
        formattedData = line.substring(index , numberIndex).trim();
        try{
            validateNumber();
        }
        catch(InvalidNumberFormatException e){
            e.getMessage();
        }

    }

    private boolean validateNumber() throws InvalidNumberFormatException {
        final Pattern pattern = Pattern.compile("(\\+|-)?([0-9]+(\\.[0-9]+))\n");
        if (! formattedData.matches("(\\+|-)?([0-9]+(\\.[0-9]+))")){
            throw new InvalidNumberFormatException(formattedData + " is not a valid numeric value");
        }
        return true;
    }
    @Override
    public String format() {

        try{
            double d = Double.parseDouble(formattedData.trim());
            return new Double(d).toString();
        }catch(NumberFormatException e){
            throw new NumberFormatException("Number Format Exception...");
        }
    }
}
