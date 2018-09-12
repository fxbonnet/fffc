package com.rajashekars.fileformatter;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.rajashekars.fileformatter.metadata.Column;
import com.rajashekars.fileformatter.metadata.DataFormat;
import com.rajashekars.fileformatter.metadata.InvalidMetadataFormatException;
import com.rajashekars.fileformatter.metadata.Metadata;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class FileFormatterImpl implements FileFormatter {

    private static final Logger LOG = LoggerFactory.getLogger(FileFormatterImpl.class);

    private Metadata metadata;

    public FileFormatterImpl(Metadata metadata) {
        this.metadata = metadata;
    }

    @Override
    public String convertToCsv(InputStream metadataStream, InputStream dataStream) {
        validateStreams(metadataStream, dataStream);

        loadMetadata(metadataStream);

        return convertToCsv(dataStream);
    }

    private String convertToCsv(InputStream dataStream) {
        StringWriter stringWriter = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(stringWriter, ',', '"', '"', "\r\n");

        writeCsvHeaders(csvWriter);

        try (LineIterator it = IOUtils.lineIterator(dataStream, StandardCharsets.UTF_8)) {

            if (!it.hasNext()) {
                throw new InvalidDatafileException("Empty data file");
            }

            List<Column> metadataColumns = metadata.get();

            do {
                String line = it.nextLine();

                String[] resultArray = new String[metadataColumns.size()];

                for (int i = 0, previousIndex = 0; i < metadataColumns.size(); i++) {
                    Column column = metadataColumns.get(i);
                    String dataStr = line.substring(previousIndex, previousIndex + column.getLength());

                    dataStr = dataStr.trim();

                    resultArray[i] = (column.getType().equals(DataFormat.DATE))
                            ? formatDate(dataStr)
                            : dataStr;

                    previousIndex = previousIndex + column.getLength();
                }

                csvWriter.writeNext(resultArray, false);

            } while (it.hasNext());

            csvWriter.flush();

        } catch (IOException e) {
            LOG.error("Error encountered while converting dataStream to csv", e);
            throw new FileFormatterException("Error encountered while converting dataStream to csv");
        }

        return stringWriter.toString();
    }

    private void writeCsvHeaders(CSVWriter csvWriter) {
        List<String> headers = new ArrayList<>();
        for (Column column : metadata.get()) {
            headers.add(column.getName());
        }

        csvWriter.writeNext(headers.toArray(new String[0]), false);
    }

    private void validateStreams(InputStream metadataStream, InputStream dataStream) {
        requireNonNull(metadataStream, "metadataStream");
        requireNonNull(dataStream, "dataStream");
    }

    private String formatDate(String dateStr) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // TODO extract away

        try {
            LocalDate formatDateTime = LocalDate.parse(dateStr, formatter);

            DateTimeFormatter resultFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            return resultFormatter.format(formatDateTime);
        } catch (DateTimeParseException exception) {
            throw new InvalidDatafileException("Invalid date format of: " + dateStr);
        }
    }

    private void loadMetadata(InputStream metadataStream) {

        try (InputStreamReader isr = new InputStreamReader(metadataStream, StandardCharsets.UTF_8);
             CSVReader reader = new CSVReader(isr)) {

            String[] nextLine = reader.readNext();

            if (nextLine == null) {
                throw new InvalidMetadataFormatException("Empty metadata file");
            }

            do {
                if (nextLine.length != 3) {
                    throw new InvalidMetadataFormatException("Number of columns: " + nextLine.length + ", expected 3 columns");
                }
                Column column = new Column();
                column.setName(nextLine[0]);
                column.setLength(Integer.parseInt(nextLine[1]));
                column.setType(DataFormat.create(nextLine[2]));

                metadata.add(column);
            } while ((nextLine = reader.readNext()) != null);

        } catch (IOException ex) {
            LOG.error("Error encountered while loading metadataStream", ex);
            throw new FileFormatterException("Error encountered while loading metadataStream");
        }
    }
}
