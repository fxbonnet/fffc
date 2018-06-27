package test.file.converter;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import test.file.model.MetaData;

/**
 * Tool to convert fixed file format files to a csv file based on a metadata file describing its structure.
 */
public class FixedFileFormatConverter {

    private static final SimpleDateFormat INPUT_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat OUTPUT_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    private static final String CHARSET_UTF8 = "UTF8";
    private static final String INPUT_DATE_REGEX = "\\d{4}-\\d{2}-\\d{2}";
    private static final String INPUT_WEIGHT_REGX = "[0-9]+.[0-9]+";
    private static final String SPACE_REGX = "\\s+";
    private static final String DATE_TYPE = "date";
    private static final String NUMERIC_TYPE = "numeric";
    private static final String COMMA = ",";

    public void convertFile(String inputFileName, String metadataFileName, String outputFileName) throws ParseException {
        String inputLine;

        Pattern datePattern = Pattern.compile(INPUT_DATE_REGEX);
        Pattern numericPattern = Pattern.compile(INPUT_WEIGHT_REGX);

        File fileDir = new File(inputFileName);
        try (BufferedReader inputFileReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileDir), CHARSET_UTF8));
             FileReader fileReader = new FileReader(metadataFileName);
             BufferedReader metadataReader = new BufferedReader(fileReader);
             PrintWriter outWriter = new PrintWriter(outputFileName)
        ) {
            // get meta data from metadata file
            List<MetaData> metaDatas = getMetaData(metadataReader);
            // write the headers to the output file
            writeHeaderToFile(outWriter, metaDatas);

            Queue<String> dateList = new LinkedList<>();
            Queue<String> numericList = new LinkedList<>();
            Queue<String> tokens = new LinkedList<>();

            while ((inputLine = inputFileReader.readLine()) != null) {
                // find all the date in the row
                Matcher dateMatcher = datePattern.matcher(inputLine);
                while (dateMatcher.find()) {
                    String dateString = dateMatcher.group();
                    dateList.offer(dateString);
                    inputLine = inputLine.replaceFirst(dateString, StringUtils.EMPTY);
                }

                // find all the numeric values in the row
                Matcher numericMatcher = numericPattern.matcher(inputLine);
                while (numericMatcher.find()) {
                    String numericString = numericMatcher.group();
                    numericList.offer(numericString);
                    inputLine = inputLine.replaceFirst(numericString, StringUtils.EMPTY);
                }

                // the remaining are string separated by one or more space
                for (String value : inputLine.split(SPACE_REGX)) {
                    tokens.offer(value);
                }

                validateColumnMatch(metaDatas, dateList, numericList, tokens);

                try {
                    // try write csv row
                    writeCsvEntry(outWriter, metaDatas, dateList, numericList, tokens);
                } catch (ParseException e) {
                    System.err.println("Format of the input date is incorrect " + e);
                    throw e;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void validateColumnMatch(List<MetaData> metaDatas, Queue<String> dateList, Queue<String> numericList, Queue<String> tokens) {
        String errorMessage = "The number of columns from the input file does not match the number of columns specified in the metadata file.";
        if (metaDatas.size() != dateList.size() + numericList.size() + tokens.size()) {
            System.err.println(errorMessage);
            throw new RuntimeException(errorMessage);
        }
    }

    /**
     * Write a CSV entry
     *
     * @param outWriter   output file writer
     * @param metaDatas   - meta data
     * @param dateList    - a list of dates
     * @param numericList - a list of numeric values
     * @param tokens      - a list of string tokens
     * @throws ParseException if the input date is in a different format, exception will be thrown for this row, and the row will be skip for write.
     */
    private void writeCsvEntry(PrintWriter outWriter, List<MetaData> metaDatas,
                               Queue<String> dateList, Queue<String> numericList,
                               Queue<String> tokens) throws ParseException {

        List<String> elements = new ArrayList<>();
        // using the type of row in the metadata file
        // put together a list of element in order specified by the metadata file
        for (MetaData metaData : metaDatas) {
            if (StringUtils.equalsIgnoreCase(metaData.getType(), DATE_TYPE)) {
                String dateStr = dateList.poll();
                Date date = INPUT_FORMAT.parse(dateStr);
                elements.add(OUTPUT_FORMAT.format(date));
            } else if (StringUtils.equalsIgnoreCase(metaData.getType(), NUMERIC_TYPE)) {
                elements.add(numericList.poll());
            } else {
                elements.add(StringUtils.substring(tokens.poll(), 0, metaData.getLength()));
            }
        }
        // once completed, join the list together with comma separator
        outWriter.println(StringUtils.join(elements, COMMA));
    }

    private void writeHeaderToFile(PrintWriter outWriter, List<MetaData> metaDatas) {
        List<String> headerList = metaDatas.stream().map(MetaData::getHeader).collect(Collectors.toList());
        String headerLine = StringUtils.join(headerList, COMMA);
        outWriter.println(headerLine);
    }

    /**
     * Get meta data from metadata file.
     */
    private List<MetaData> getMetaData(BufferedReader metadataReader) throws IOException {
        String metadataLine;

        List<MetaData> metaDatas = new ArrayList<>();
        while ((metadataLine = metadataReader.readLine()) != null) {
            String[] metadataString = StringUtils.split(metadataLine, COMMA);
            metaDatas.add(new MetaData(metadataString[0], new Integer(metadataString[1]), metadataString[2]));
        }

        return metaDatas;
    }
}
