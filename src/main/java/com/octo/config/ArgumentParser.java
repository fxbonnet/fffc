package com.octo.config;

import com.octo.coverter.FileFormat;
import com.octo.exception.FixedFileFormatCoverterException;

import java.util.Arrays;
import java.util.Objects;

/**
 * This class is responsible for parsing the input arguments for the file converter.
 * <pre>
 *     Mandatory parameters:
 *     -iFile=<path>
 *         absolute path to the source file to be converted
 *     -iFileMetaData=<path>
 *         absolute path to the metadata for the source file
 *     -oFile=<output file>
 *         absolute path to the target file to be created
 *     -oFileFormat=csv
 *         supported format: csv
 * </pre>
 */
public class ArgumentParser {

    private static final String INPUT_FILE = "-iFile";
    private static final String INPUT_FILE_FORMAT = "-iFileMetaData";
    private static final String OUTPUT_FILE = "-oFile";
    private static final String OUTPUT_FILE_FORMAT = "-oFileFormat";
    private static final String ARG_SEPARATOR = "=";


    private String inputFileName;
    private String inputFormatFileName;
    private String outputFileName;
    private String outputFileFormatStr;
    private FileFormat outputFileFormat;


    /**
     * Parse the input arguments
     * @param args
     * @return
     */
    public Boolean parseArgs(String[] args) {
        if (!Objects.isNull(args)) {

            Arrays.stream(args)
                    .map(s -> s.split(ARG_SEPARATOR))
                    .filter(s -> s.length > 1)
                    .forEach(arg -> {
                        if (INPUT_FILE.equals(arg[0])) {
                            inputFileName = arg[1];
                        } else if (INPUT_FILE_FORMAT.equals(arg[0])) {
                            inputFormatFileName = arg[1];
                        } else if (OUTPUT_FILE.equals(arg[0])) {
                            outputFileName = arg[1];
                        } else if (OUTPUT_FILE_FORMAT.equals(arg[0])) {
                            outputFileFormatStr = arg[1];
                        }
                    });

        }

        if (Objects.isNull(inputFileName) || Objects.isNull(inputFormatFileName)
                || Objects.isNull(outputFileName) || Objects.isNull(outputFileFormatStr)) {
            System.out.println("\nUsage: \n\t java -jar fffc.jar -iFile=<input file> -iFileMetaData=<metadata file> -oFile=<output file> oFileFormat=<> \n\n");
            return false;
        }

        try {
            outputFileFormat = FileFormat.valueOf(outputFileFormatStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new FixedFileFormatCoverterException("Unsupported output file format :" + outputFileFormatStr);
        }

        return true;
    }

    public String getInputFileName() {
        return inputFileName;
    }

    public String getInputFormatFileName() {
        return inputFormatFileName;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public FileFormat getOutputFileFormat() {
        return outputFileFormat;
    }
}
