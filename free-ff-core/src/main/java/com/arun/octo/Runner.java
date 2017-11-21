package com.arun.octo;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class Runner {

    private static String metaDataFile;
    private static String dataFile;
    private static Parser parser;
    private static String outPutFileName;

    public static void main(String[] args) {
        if (args.length == 0) {
            PrintUsage();
            System.exit(0);
        }
        validateArguements(args);
        try {
            parser = new Parser(FileUtils.getFile(metaDataFile), FileUtils.getFile(dataFile));
            MetaData metaData = parser.parseMetaData();
            List<String> records = parser.parseData(metaData);
            parser.write(metaData.getColumns(), records, outPutFileName);
            System.out.println("CSV File Written: " + outPutFileName);
        } catch (Exception e) {
            System.out.println("e = " + e.getMessage());
        }
    }

    private static void validateArguements(String[] args) {
        metaDataFile = StringUtils.defaultString(args[0]);
        dataFile = StringUtils.defaultString(args[1]);
        outPutFileName = StringUtils.defaultString(args[2]);
        if (StringUtils.isAnyBlank(metaDataFile, dataFile, outPutFileName)) {
            PrintUsage();
        }
    }

    private static void PrintUsage() {
        System.out.println("All three parameters must be specified");
        System.out.println("metaDataFile = " + metaDataFile);
        System.out.println("dataFile = " + dataFile);
        System.out.println("outPutFileName = " + outPutFileName);
    }
}
