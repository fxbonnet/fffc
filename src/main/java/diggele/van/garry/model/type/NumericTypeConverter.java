package diggele.van.garry.model.type;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;

public class NumericTypeConverter implements TypeConverter {

    @Override
    public String convert(@NotNull final String aValue) {
        Preconditions.checkNotNull(aValue);
        // NOTE: I know i can convert it straight to a number, but may want to do some object operations in
        // the future.. speed not a requirements at this stage anyhow.
        float number = Float.parseFloat(aValue.trim().replace(",", ""));
        return Float.toString(number);
    }

    @Override
    public String toString() {
        return "NumericTypeConverter{}";
    }
}
