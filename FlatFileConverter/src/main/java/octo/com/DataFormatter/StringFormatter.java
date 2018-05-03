package octo.com.DataFormatter;

import octo.com.utils.Metadata;

import java.util.regex.Pattern;

/**
 * Represents a String field with. Encapsulates string specific validation and output formatting
         */
public class StringFormatter extends DataFormatter {

    private static final Pattern pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
    String  formattedData;

    public StringFormatter(String line, int index, Metadata.MetadataFields fieldMetaData) {
        formattedData = line.substring(index , index + fieldMetaData.columnSize).trim();
    }

    @Override
    public String format() {

        if (pattern.matcher(formattedData).find()){
            return "\"" + formattedData + "\"";
        }
        return formattedData;
    }
}
