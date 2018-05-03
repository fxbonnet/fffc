package octo.com.facade;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class FileConverterFacadeTest {

    String inputFilePath = "src\\main\\test-data\\input.dat";
    String metadataFilePath = "src\\main\\test-data\\metadata.csv";
    String outputFilePath = "src\\main\\test-data\\output.csv";
    String testFilePath = "src\\main\\test-data\\outputTest.csv";
    String testFilePath1 = "src\\main\\test-data\\outpTest.csv";
    final String SUCCESS_STATUS = "File Converted Successfully.";

    FileConverterFacade fcFacade;

    @Test
    public void convertFileUsingMetadata() {
        fcFacade = new FileConverterFacade(metadataFilePath, inputFilePath, outputFilePath);
        String status = null;
        try {
            status = fcFacade.convertFileUsingMetadata();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        Assert.assertEquals(SUCCESS_STATUS, status);
        try {
            Assert.assertTrue(FileUtils.contentEquals(new File(outputFilePath), new File(testFilePath)));
            new File(outputFilePath).delete();
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    @Test
    public void convertFileUsingInvalidMetadata() {
        String invalidMetadataFilePath = "src\\main\\test-data\\invalid-metadata\\metadata.csv";

        fcFacade = new FileConverterFacade(invalidMetadataFilePath , inputFilePath, outputFilePath);
        String status = null;
        try {
            status = fcFacade.convertFileUsingMetadata();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        Assert.assertNotEquals(SUCCESS_STATUS, status);
        Assert.assertEquals(null, status);
        Assert.assertFalse(fcFacade.validateIfFileExists(outputFilePath));

    }

    @Test
    public void validateIfFileExists() {
        fcFacade = new FileConverterFacade(metadataFilePath, inputFilePath, outputFilePath);
        Assert.assertTrue(fcFacade.validateIfFileExists(inputFilePath));
        Assert.assertTrue(fcFacade.validateIfFileExists(outputFilePath));
        Assert.assertFalse(fcFacade.validateIfFileExists(testFilePath1));

    }
}