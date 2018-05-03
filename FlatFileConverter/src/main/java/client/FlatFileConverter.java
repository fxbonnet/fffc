package client;

import octo.com.facade.FileConverterFacade;

import java.util.Scanner;
import org.apache.log4j.Logger;

public class FlatFileConverter {
    final static Logger LOG = Logger.getLogger(FlatFileConverter.class);
    static String metadataFilePath;
    static String inputFilePath;
    static String outputFilePath;


    public static void main(String args[]){

       // getInputFromUser();
        inputFilePath = "src\\main\\test-data\\input.dat";
        metadataFilePath = "src\\main\\test-data\\metadata.csv";
        outputFilePath = "src\\main\\test-data\\output.csv";
        FileConverterFacade fileConverterFacade = new FileConverterFacade(metadataFilePath,inputFilePath,outputFilePath);
        String status = null;
        try {
            status = fileConverterFacade.convertFileUsingMetadata();
        }catch(Exception e){
            if(LOG.isDebugEnabled()){
                LOG.error(e);
            }
        }
        if(null != status) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("File Conversion Status : " + status);
            }
        }else{
            LOG.error("Unable to convert the file. Please see logs for possible errors");
        }

    }

    public static void getInputFromUser(){
        Scanner scanner = new Scanner(System.in);
        if(LOG.isDebugEnabled()){
            LOG.debug("Enter the Metadata file path");
        }
        metadataFilePath = scanner.next();

        if(LOG.isDebugEnabled()){
            LOG.debug("Enter the Input file path");
        }
        inputFilePath = scanner.next();

        if(LOG.isDebugEnabled()){
            LOG.debug("Enter the Output file path");
        }
        outputFilePath = scanner.next();

    }



}
