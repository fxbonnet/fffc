package diggele.van.garry.model;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class MetaDataFileDefinition {

    final private Logger logger = LoggerFactory.getLogger(MetaDataFileDefinition.class);

    private Map<Integer, Column> definitions = new HashMap<>();

    public MetaDataFileDefinition addColumnDefinition(final Column aColumn) {
        Preconditions.checkNotNull(aColumn);
        if (definitions.containsKey(aColumn.getPosition())) {
            logger.error("Column definition already defined at the requested position: {}", aColumn);
            throw new IllegalArgumentException("Column position already defined");
        }
        definitions.put(aColumn.getPosition(), aColumn);
        return this;
    }

    public MetaDataFileDefinition removeColumnDefinition(final Column aColumn) {
        definitions.remove(aColumn.getPosition());
        return this;
    }

    public Map<Integer, Column> getDefinitions() {
        return definitions;
    }

    @Nullable
    public Column getColumnAtPosition(int aPosition) {
        return definitions.get(aPosition);
    }

    public int getNumberOfColumns() {
        return definitions.size();
    }

    @Override
    public String toString() {
        return "MetaDataFileDefinition{" +
                "definitions=" + definitions +
                '}';
    }
}
