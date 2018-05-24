package com.octo.fffc.model.transformer;

import com.octo.fffc.exceptions.FormatterException;
import com.octo.fffc.exceptions.TransformationException;
import com.octo.fffc.model.metadata.Metadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Function;

import static com.octo.fffc.model.formatter.FormatterFactory.formatter;
import static java.lang.String.format;
import static java.util.Collections.unmodifiableList;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Function class that converts the give input to {@link String[]} based on
 * the given {@link Metadata}
 *
 * @author bharath.raghunathan
 * @version 1.0.0
 * @since 1.0.0
 */
final class RowProcessor implements Function<String, String[]> {

    private static final Logger logger = LoggerFactory.getLogger(RowProcessor.class);
    private final List<Metadata> metadatas;

    RowProcessor(List<Metadata> metadatas) {
        this.metadatas = unmodifiableList(metadatas);
    }

    @Override
    public String[] apply(String line) {

        if (isBlank(line)) {
            throw new TransformationException("Record empty or null. Cannot transform record");
        }

        if (line.length() < metadatas.get(metadatas.size() - 1).getEndIndex()) {
            throw new TransformationException(format("Input line incomplete. Cannot transform record %s", line));
        }

        return metadatas.stream()
                .map(metadata -> extract(line, metadata))
                .toArray(String[]::new);
    }

    private String extract(String line, Metadata metadata) {
        try {
            Function<String, String> formatter = formatter(metadata.getDataType());
            String record = line.substring(metadata.getStartIndex(), metadata.getEndIndex());
            return formatter.apply(record);
        } catch (FormatterException ex) {
            logger.error("Unable to format input record", ex);
            throw new TransformationException("Unable to format input record", ex);
        }
    }
}