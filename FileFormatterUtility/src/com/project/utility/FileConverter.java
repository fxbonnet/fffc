package com.project.utility;

import com.project.utility.exception.IncorrectDataFormatException;
import com.project.utility.utils.DateUtils;
import com.project.utility.exception.FileFormatterException;
import com.project.utility.utils.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;

import java.util.ArrayList;

/**
 * FileConverter will convert the given input file to any
 * other FileFormatWriter implementation. Below it uses
 * a CSV format
 * Created by rssridh on 12/3/17.
 */
public class FileConverter {
    private final String inputFileName;
    private final String outputFileName;
    private final String metaDataFileName;

    private final String STRING_TYPE = "string";
    private final String NUMERIC_TYPE = "numeric";
    private final String DATE_TYPE = "date";

    public FileConverter(String inputFileName, String outputFileName, String metaDataFileName) {
        this.inputFileName = inputFileName;
        this.outputFileName = outputFileName;
        this.metaDataFileName = metaDataFileName;
    }

    /**
     * Converts the input string to CSV file
     * @throws FileFormatterException
     * @throws IncorrectDataFormatException
     */
    public void convert() throws FileFormatterException, IncorrectDataFormatException {
        MetaDataReader metaDataReader = new MetaDataReader(metaDataFileName);
        ArrayList<FileStructure> fileStructure =  metaDataReader.getFileStructure();

        try {
            File inputDataFile = new File(inputFileName);
            BufferedReader inputDatabufferedReader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(inputDataFile), "UTF8"));

            FileFormatWriter fileFormatWriter = new CSVWriter(outputFileName);

            // Writing the file title.
            String fileTitle = metaDataReader.getTitleRow(fileStructure);
            fileFormatWriter.writeLine(fileTitle);

            //Converting the text data to csv
            String readInputDataLine;
            int count = 0;
            while ((readInputDataLine = inputDatabufferedReader.readLine()) != null) {
                StringBuilder s = new StringBuilder();
                int startIndex = 0;

                while (count < fileStructure.size()) {
                    if (count != 0) {
                        s.append(fileFormatWriter.getDelimiter());
                    }

                    int len = fileStructure.get(count).getLength();
                    String str = readInputDataLine.substring(startIndex, (startIndex + len));
                    str = formatDataBasedOnType(str, fileStructure.get(count).getType());
                    s.append(str);
                    startIndex += len;
                    count++;
                }
                count = 0;
                fileFormatWriter.writeLine(s.toString());
            }
            fileFormatWriter.closeFile();

        } catch (FileNotFoundException ex) {
            throw new FileFormatterException("Unable to find the metadata file - " + inputFileName
                    + ". Please check if the file is in the path provided -" + ex.getMessage());
        } catch (IOException io) {
            throw new FileFormatterException("We ran into an unexpected error while converting the "
                    + "input file to output csv file - " + io.getMessage());
        }

    }

    // Can be annotated with @VisibleForTesting
    /**
     * Validates and formats based on supported data type.
     * @param dataString
     * @param type
     * @return formatted string
     * @throws IncorrectDataFormatException
     */
    public String formatDataBasedOnType(String dataString, String type) throws IncorrectDataFormatException {
        if (STRING_TYPE.equalsIgnoreCase(type)) {
            return StringUtils.formatString(dataString.trim());
        } else if (NUMERIC_TYPE.equalsIgnoreCase(type)) {
            return dataString;
        } else if (DATE_TYPE.equalsIgnoreCase(type)) {
            return DateUtils.convertDateToYearFormat(dataString);
        } else {
            throw new IncorrectDataFormatException("The file contains unknown data type.");
        }
    }
}
