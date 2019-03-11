package com.github.mpawlucz.octo.fffc;

import com.github.mpawlucz.octo.fffc.config.MetaSettings;
import com.github.mpawlucz.octo.fffc.domain.AbstractColumnParser;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Fffc {

    private MetaSettings metaSettings;

    public Fffc(MetaSettings metaSettings) {
        this.metaSettings = metaSettings;
    }

    public static void main(String[] args) {
        if (args.length < 3){
            System.out.println("Usage: java -jar fffc.jar <metadata file> <data file> <output file>");
            return;
        }
        final String metadataFile = args[0];
        final String dataFile = args[1];
        final String outputFile = args[2];

        final Fffc fffc = new Fffc(new MetaSettings(new File(metadataFile)));
        try (
                BufferedReader br = new BufferedReader(new FileReader(new File(dataFile)));
                Writer writer = new PrintWriter(outputFile, "utf-8");
                CSVPrinter csvWriter = CSVFormat.RFC4180
                    .withQuoteMode(QuoteMode.MINIMAL)
                    .withHeader(fffc.getColumnNames())
                    .print(writer);
        ) {
            String readLine;
            while ((readLine = br.readLine()) != null) {
                final List<Object> parsed = fffc.parse(readLine);
                csvWriter.printRecord(parsed);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private List<Object> parse(String line) {
        final ArrayList<Object> result = new ArrayList<>();
        int begin = 0;
        for (AbstractColumnParser parser : metaSettings.getTypes()) {
            final Integer length = parser.getLength();
            final String substring = line.substring(begin, begin+length);
            begin += length;
            result.add(parser.parse(substring));
        }
        return result;
    }

    private String[] getColumnNames(){
        final List<String> columnNames = metaSettings.getTypes().stream()
                .map(x -> x.getName()).collect(Collectors.toList());
        return columnNames.toArray(new String[columnNames.size()]);
    }

}
