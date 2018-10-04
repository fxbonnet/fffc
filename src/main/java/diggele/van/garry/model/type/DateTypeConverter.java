package diggele.van.garry.model.type;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class DateTypeConverter implements TypeConverter {

    private String inputPattern = "yyyy-mm-dd";

    private String outputPattern = "dd/mm/yyyy";

    @Override
    public String convert(@NotNull final String aValue) {
        Preconditions.checkNotNull(aValue);
        DateTime dateTime = DateTime.parse(aValue, DateTimeFormat.forPattern(inputPattern));
        return dateTime.toString(outputPattern);
    }

    public String getInputPattern() {
        return inputPattern;
    }

    public void setInputPattern(final String aInputPattern) {
        inputPattern = aInputPattern;
    }

    public String getOutputPattern() {
        return outputPattern;
    }

    public void setOutputPattern(final String aOutputPattern) {
        outputPattern = aOutputPattern;
    }

    @Override
    public String toString() {
        return "DateTypeConverter{" +
                "inputPattern='" + inputPattern + '\'' +
                ", outputPattern='" + outputPattern + '\'' +
                '}';
    }
}
