package org.octo.sample;

import org.octo.processor.FileProcessor;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class ParsingFixedWidthFileDemo {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ParsingFixedWidthFileDemo.class);

    public static void main(String... args) throws Exception {
//        String metadataFile = "src/main/resources/samples/metadata.txt";
//        String inputFile = "src/main/resources/samples/data.txt";
//        String outputFile = "src/main/resources/samples/output.txt";
        String metadataFile = System.getProperty("metadata");
        String inputFile = System.getProperty("input");
        String outputFile = System.getProperty("output");

        if(null != metadataFile && !metadataFile.trim().equals("")
                && null != inputFile && !inputFile.trim().equals("")
                && null != outputFile && !outputFile.trim().equals("")) {

            logger.debug("metadataFile : "+ metadataFile);
            logger.debug("inputFile : " + inputFile);
            logger.debug("outputFile : " + outputFile);

            //Converting Fixed Width File to CSV file
            long start=System.nanoTime();
            FileProcessor fileProcessor = new FileProcessor(metadataFile);
            fileProcessor.parseFixedWidthFile(inputFile, outputFile);
            long elapsedTime=System.nanoTime()-start;
            logger.debug("Time taken: "+elapsedTime + " in neno seconds");
            logger.debug("which is " + TimeUnit.SECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS) + " seconds");
        } else {
            logger.error("Invalid VM arguments passed");
        }
    }
}