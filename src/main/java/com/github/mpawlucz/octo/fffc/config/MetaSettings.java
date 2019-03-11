package com.github.mpawlucz.octo.fffc.config;

import com.github.mpawlucz.octo.fffc.domain.AbstractColumnParser;
import com.github.mpawlucz.octo.fffc.domain.parser.DateColumnParser;
import com.github.mpawlucz.octo.fffc.domain.parser.NumericColumnParser;
import com.github.mpawlucz.octo.fffc.domain.parser.StringColumnParser;
import com.github.mpawlucz.octo.fffc.exception.MetadataFileException;
import lombok.Getter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MetaSettings {

    @Getter
    private final List<AbstractColumnParser> types;

    public MetaSettings(File metaFile) {
        final ArrayList<AbstractColumnParser> types = new ArrayList<>();
        try {
            final CSVParser parsedFile = CSVParser.parse(
                    metaFile,
                    Charset.forName("utf-8"),
                    CSVFormat.DEFAULT
                            .withSkipHeaderRecord(false)
                            .withAllowMissingColumnNames()
                            .withDelimiter(',')
                            .withIgnoreEmptyLines()
            );
            for (CSVRecord line : parsedFile) {
                types.add(createColumnParser(line));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.types = Collections.unmodifiableList(types);
    }

    private Integer getInteger(String integerString) {
        return Integer.valueOf(integerString);
    }

    private AbstractColumnParser createColumnParser(CSVRecord record){
        final String columnType = record.get(2);
        final String columnName = record.get(0);
        final Integer columnLength = getInteger(record.get(1));

        // todo extract as factory
        if (StringColumnParser.TYPE_NAME.equals(columnType)){
            return StringColumnParser.builder()
                    .name(columnName)
                    .length(columnLength)
                    .build();
        }
        if (DateColumnParser.TYPE_NAME.equals(columnType)){
            return DateColumnParser.builder()
                    .name(columnName)
                    .length(columnLength)
                    .build();
        }
        if (NumericColumnParser.TYPE_NAME.equals(columnType)){
            return NumericColumnParser.builder()
                    .name(columnName)
                    .length(columnLength)
                    .build();
        }

        throw new MetadataFileException("unknown column type: " + columnType);
    }

}
