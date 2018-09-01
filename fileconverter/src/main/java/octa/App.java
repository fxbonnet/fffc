package octa;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.NumberUtils;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@SpringBootApplication
public class App implements CommandLineRunner {
    private static Logger LOG = LoggerFactory.getLogger(App.class);

    public static BiFunction<String, List<Header>, String[]> dataConverter = (lineItem, headers) -> {
        String[] data = new String[headers.size()];
        int index = 0;
        for(int i=0; i<headers.size(); i++) {
            Header header = headers.get(i);
            int size = header.getSize();
            data[i] = lineItem.substring(index, index+size).trim();
            if(header.getDataType().equals("date")) {
                data[i] = formatDateString(data[i]);
            } else if (header.getDataType().equals("numeric")) {
                Double d = Double.parseDouble(data[i]);
                data[i] = d.toString().substring(0, data[i].length());
            }
            index += size;
        }
        return data;
    };

    public static void main(String[] args) {
        try {
            LOG.info("Process file..");
            SpringApplication.run(App.class, args);
            LOG.info("Process completed.");
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run(String... args) throws IOException {
        String inputFile = "c:/temp/data.txt";
        String outputFile = "c:/temp/output.csv";
        String headerFile = "c:/temp/header.csv";
        LineIterator lineIterator = FileUtils.lineIterator(new File(inputFile), "UTF-8");

        try {
            List<Header> headers = getHeaders(headerFile);
            CSVPrinter csvPrinter = new CSVPrinter(Files.newBufferedWriter(Paths.get(outputFile)),
                    CSVFormat.DEFAULT
                            .withHeader(getHeaderText(headers)));
            processLineItems(dataConverter, lineIterator, csvPrinter, headers);
        } catch(Exception ex) {
            Files.deleteIfExists(Paths.get(outputFile));
            throw ex;
        } finally {
            LineIterator.closeQuietly(lineIterator);
        }
    }

    private void processLineItems(BiFunction<String, List<Header>, String[]> dataConverter, LineIterator lineIterator,
                             CSVPrinter csvPrinter, List<Header> headers) throws IOException {
        while (lineIterator.hasNext()) {
            String line = lineIterator.nextLine();
            String[] lineItems = dataConverter.apply(line, headers);
            csvPrinter.printRecord(lineItems);
            csvPrinter.flush();
        }
    }

    private String[] getHeaderText(List<Header> headers) {
        return headers
                .stream()
                .map(h -> h.getText()).collect(Collectors.toList())
                .toArray(new String[headers.size()]);
    }

    private List<Header> getHeaders(String fileName) throws IOException {
        Reader reader = Files.newBufferedReader(Paths.get(fileName));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
        List<Header> headers = new ArrayList<>();

        for (CSVRecord csvRecord : csvParser) {
            headers.add(new Header(csvRecord.get(0), Integer.parseInt(csvRecord.get(1)), csvRecord.get(2)));
        }

        return headers;
    }

    private static String formatDateString(String dateString) {
        DateTimeFormatter sourceFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(dateString, sourceFormatter);
        return date.format(targetFormatter);
    }
}