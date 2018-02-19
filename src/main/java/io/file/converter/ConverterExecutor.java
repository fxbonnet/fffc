package io.file.converter;

import io.file.converter.exception.DataFormatException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ConverterExecutor {

    private static final String dateSourceFormat = "yyyy-mm-dd";
    private static final String dateTargetFormat = "dd/mm/yyyy";
    private static final DateFormat dateSource = new SimpleDateFormat(dateSourceFormat);
    private static final DateFormat dateTarget = new SimpleDateFormat(dateTargetFormat);
    private static final String[] headersDescriptor = {"name", "size", "type"};
    private List<HeaderDescriptor> headerDescriptors;
    private String pathToHeaderDescriptor;
    private String pathDataSource;
    private String pathDataTarget;

    public ConverterExecutor(String pathToHeaderDescriptor, String pathDataSource, String pathDataTarget) {
        this.pathToHeaderDescriptor = pathToHeaderDescriptor;
        this.pathDataSource = pathDataSource;
        this.pathDataTarget = pathDataTarget;
        loadHeaderDescriptors();
    }

    private void loadHeaderDescriptors() {
        try {
            Reader readerContract = Files.newBufferedReader(
                    Paths.get(pathToHeaderDescriptor)
            );
            CSVParser csvParserContract = new CSVParser(readerContract, CSVFormat.DEFAULT
                    .withHeader(headersDescriptor)
                    .withTrim());
            headerDescriptors = csvParserContract.getRecords().stream()
                    .map(
                            p -> new HeaderDescriptor(
                                    p.get(headersDescriptor[0]),
                                    Integer.parseInt(p.get(headersDescriptor[1])),
                                    p.get(headersDescriptor[2])))
                    .collect(Collectors.toList());
            System.out.println("headerDescriptors : " + headerDescriptors);
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    public void convert() {
        try {
            LineIterator it = FileUtils.lineIterator(
                    new File(pathDataSource)
                    , "UTF-8");
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(pathDataTarget));
            CSVPrinter target = new CSVPrinter(writer, CSVFormat.DEFAULT
                    .withTrim()
                    .withHeader(headerDescriptors.stream().map(t -> t.name).toArray(String[]::new)));
            parseToTarget(it, target);
        } catch (DataFormatException e) {
            throw e;
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    private void parseToTarget(LineIterator it, CSVPrinter target) {
        final long start = System.currentTimeMillis();
        long counter = 1;
        try {
            while (it.hasNext()) {
                String line = it.nextLine();
                int seek = 0;
                List<String> values = new ArrayList<>();
                for (HeaderDescriptor headerDescriptor : headerDescriptors) {
                    values.add(parserField(line.substring(seek, seek + headerDescriptor.size), headerDescriptor.type));
                    seek += headerDescriptor.size;
                }
                target.printRecord(values.toArray());
                counter++;
                // flush every 50 lines or last line
                if (!it.hasNext() || (counter % 50 == 0))
                    target.flush();
            }
        } catch (DataFormatException e) {
            throw new DataFormatException("Line " + counter + ": " + e.getMessage(), e);
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        } finally {
            LineIterator.closeQuietly(it);
            final long elapsedTime = System.currentTimeMillis() - start;
            System.out.println("ParseToTarget elapsedTime:" + TimeUnit.MILLISECONDS.toSeconds(elapsedTime) + "s");
        }
    }

    private String parserField(String value, String type) {

        switch (DataType.valueOf(type.toUpperCase())) {
            case DATE:
                try {
                    return dateTarget.format(dateSource.parse(value));
                } catch (ParseException e) {
                    throw new DataFormatException("Validation error, this value should be a " +
                            type + "with the following format : " + dateSourceFormat + ", found -> " + value, e);
                }
            case NUMERIC:
                try {
                    Float.parseFloat(value);
                } catch (NumberFormatException e) {
                    throw new DataFormatException("Validation error, this value should be a " +
                            type + ", found -> " + value);
                }
                return value;
            case STRING:
            default:
                return value;
        }
    }

    enum DataType {
        DATE, STRING, NUMERIC
    }

    @Getter
    @AllArgsConstructor
    @ToString
    static class HeaderDescriptor {
        private final String name;
        private final int size;
        private final String type;
    }
}