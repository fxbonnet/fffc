package diggele.van.garry.model.type;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

/**
 * ColumnTypeFactory supports the creation of different types.
 * A simple factory easily extensible to support additional types. A registry is utilised currently defined in
 * code.
 * <p>
 * The supported queues are:
 * - "date"
 * - "numeric"
 * - "string"
 * <p>
 * Invalid options will throw associated exceptions.
 *
 * @author Garry van Diggele
 */
public class ColumnTypeFactory {

    private static Map<String, TypeConverter> registry = new HashMap<>(
            ImmutableMap.of(
                    "date", new DateTypeConverter(),
                    "numeric", new NumericTypeConverter(),
                    "string", new StringTypeConverter())
    );

    public static TypeConverter getNewInstanceType(String aTypeConverter) throws IllegalAccessException, InstantiationException {
        Preconditions.checkArgument(checkTypeRegistry(aTypeConverter));
        TypeConverter result = registry.get(aTypeConverter);
        return result.getClass().newInstance();
    }

    public static boolean checkTypeRegistry(final String aType) {
        return registry.containsKey(aType);
    }
}
