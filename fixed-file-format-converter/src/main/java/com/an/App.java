package com.an;

import com.an.service.FileProcessorService;
import com.an.service.FileProcessorServiceImpl;

import java.io.File;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    public static void main( String[] args ) throws   Exception {

        if(args.length != 3) {
            throw new Exception("USAGE : mvn exec:java -Dexec.mainClass='com.an.App' -Dexec.args='SOURCE_FILE METADATA_FILE DESTINATION_FILE'");
        }else {

            LOGGER.log(Level.INFO, "INPUT PARAMETERS");
            LOGGER.log(Level.INFO, "SOURCE FILE : " + args[0]);
            LOGGER.log(Level.INFO, "METADATA FILE : " + args[1]);
            LOGGER.log(Level.INFO, "DESTINATION FILE : " + args[2]);

            File sourceFile = new File(args[0]);
            if(!sourceFile.exists()){
                throw new Exception("Source File [ "+ args[0]+" ] does not exist");
            }

            File metadataFile = new File(args[1]);
            if(!metadataFile.exists()){
                throw new Exception("Metadata File [ "+ args[0]+" ] does not exist");
            }

            FileProcessorService fileProcessorService = new FileProcessorServiceImpl();
            fileProcessorService.processFile(sourceFile,metadataFile,args[2]);

        }

    }

}


