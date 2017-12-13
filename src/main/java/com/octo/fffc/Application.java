package com.octo.fffc;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Application {

    private static final Logger logger = Logger.getLogger(Application.class.getName());

    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println("Invalid number of arguments!");
            System.out.println("Run this application with the following arguments:");
            System.out.println("[1]: Full path to the metadata file");
            System.out.println("[2]: Full path to the input data file \n");
            System.out.println("The output file will created as the '<inputDataFile>.csv'. The input file extension will NOT be used to generate the output file name.");
            System.exit(-1);
        }

        String metadataFilePath = args[0];
        String inputFilePath = args[1];

        FixedFileConverter ffc = new FixedFileConverter(metadataFilePath, inputFilePath);
        ffc.generateCsvFile();

        logger.info("Converting " + inputFilePath + " to CSV format");

        logger.info("Application has successfully run.");

    }
}
