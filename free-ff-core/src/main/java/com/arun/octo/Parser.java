package com.arun.octo;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.substring;


public class Parser {
    @Getter
    @Setter
    private File metadataFile;
    @Getter
    @Setter
    private File dataFile;
    private final DateTimeFormatter informat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter outformat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Parser(File metadataFile, File dataFile) {
        this.metadataFile = metadataFile;
        this.dataFile = dataFile;
    }

    public MetaData parseMetaData() throws IOException {
        MetaData metaData = new MetaData();
        Reader r = new FileReader(metadataFile);
        Object[] records = IteratorUtils.toArray(CSVFormat.RFC4180.parse(IOUtils.toBufferedReader(r)).iterator());
        Column[] columns = new Column[records.length];

        for (int i = 0; i < records.length; i++) {
            CSVRecord record = (CSVRecord) records[i];
            String columnName = record.get(0);
            long columnLength = Long.parseLong(record.get(1));
            ColumnType columnType = getColumnType(record.get(2));
            Column c = new Column(columnName, columnLength, columnType);
            columns[i] = c;
        }
        metaData.setColumns(columns);
        return metaData;
    }

    private ColumnType getColumnType(String type) {
        if (StringUtils.isNotBlank(type) && StringUtils.equalsIgnoreCase(ColumnType.DATE.toString(), type))
            return ColumnType.DATE;
        if (StringUtils.isNotBlank(type) && StringUtils.equalsIgnoreCase(ColumnType.NUMERIC.toString(), type))
            return ColumnType.NUMERIC;
        if (StringUtils.isNotBlank(type) && StringUtils.equalsIgnoreCase(ColumnType.STRING.toString(), type))
            return ColumnType.STRING;
        return ColumnType.UNIDENTIFIED;
    }

    public List<String> parseData(MetaData metaData) throws IOException {
        List<String> records = new ArrayList<>();
        try (LineIterator it = FileUtils.lineIterator(dataFile, StandardCharsets.UTF_8.name())) {
            Iterable<String> iterable = () -> (Iterator<String>) it;
            StreamSupport.stream(iterable.spliterator(), true).forEach(l -> {
                Column[] columns = metaData.getColumns();
                String[] data = new String[metaData.getColumns().length];
                int start;
                int end = 0;
                for (int i = 0; i < columns.length; i++) {
                    Column column = columns[i];
                    start = i == 0 ? 0 : end;
                    end = start + Math.toIntExact(column.getColumnLength());
                    String substring = substring(l, start, end).trim();
                    if (column.getColumnType() == ColumnType.DATE) {
                        LocalDate dateTime = LocalDate.parse(substring, informat);
                        substring = dateTime.format(outformat);
                    }
                    data[i] = substring;
                }
                records.add(Arrays.stream(data).collect(Collectors.joining(",")));
            });
        }
        return records;
    }

    public void write(Column[] columns, List<String> records, String fileName) throws IOException {
        String header = Arrays.stream(columns).map(Column::getColumnName).collect(Collectors.joining(","));
        File file = new File(fileName);
        FileUtils.write(file, format("%s\n", header), StandardCharsets.UTF_8);
        records.parallelStream().forEach(s -> {
            try {
                FileUtils.writeStringToFile(file, format("%s\n", s), StandardCharsets.UTF_8, true);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        });
    }
}
