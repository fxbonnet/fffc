package com.octoassessment;

import com.octoassessment.configuration.AppConfig;
import com.octoassessment.exception.FixedFileFormatConversionException;
import com.octoassessment.model.ConversionParams;
import com.octoassessment.service.FixedFileFormatConversionService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RunMain {

    private static Logger logger = Logger.getLogger(RunMain.class.getName());

    public static void main(String[] args) {
        try {
            ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
            ConversionParams conversionParams = new ConversionParams(
                    getFilePath("data/sample.txt"),
                    "output.csv",
                    getFilePath("data/metadata.txt"));
            ((FixedFileFormatConversionService) context.getBean("fixedFileFormatConversionService")).process(conversionParams);
        } catch (FixedFileFormatConversionException ex) {
            logger.log(Level.SEVERE, "Error processing file. Message: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private static String getFilePath(String fileName) {
        ClassLoader classLoader = RunMain.class.getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        return file.getAbsolutePath();
    }

}
