package diggele.van.garry.model.type;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringTypeConverter implements TypeConverter {

    // I'll never get a new line anyhow as the text is reading new lines. Special character
    // definition is quite broad, as such; just using the below pattern.
    private Pattern pattern = Pattern.compile("[^a-z0-9]", Pattern.CASE_INSENSITIVE);

    @Override
    public String convert(final String aValue) {
        return handleSpecialCharacter(aValue);
    }

    private String handleSpecialCharacter(String aStringToEncode) {
        Matcher matcher = pattern.matcher(aStringToEncode);
        return (matcher.find()) ? encodeString(aStringToEncode) : aStringToEncode;
    }


    private String encodeString(final String aStringToEncode) {
        return "\"" + aStringToEncode + "\"";
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(final Pattern aPattern) {
        pattern = aPattern;
    }

    @Override
    public String toString() {
        return "StringTypeConverter{" +
                "pattern=" + pattern +
                '}';
    }
}
