package octo.com.service;

import octo.com.utils.Metadata;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class FileConverterServiceTest {
    FileConverterService fileConverterService;
    String metadataFilePath = "src\\main\\test-data\\metadata.csv";

    @Test
    public void convertFile() {
        fileConverterService = new FileConverterService();
        String line = "1988-11-28Bob            Big            102.4";
        String result = "28/11/1988,Bob,Big,102.4";
        Metadata metaData = new Metadata(metadataFilePath);
        try {
            String resultFromService = fileConverterService.convertFile(line, metaData);
            Assert.assertEquals(result, resultFromService);
        }catch (Exception e){
           e.printStackTrace();
        }
    }

    @Test
    public void convertFileForInvalidDate() {
        fileConverterService = new FileConverterService();
        String line = "1970/01-01John           Smith          81.5";
        Metadata metaData = new Metadata(metadataFilePath);
        String resultFromService = null;
        try {
            resultFromService = fileConverterService.convertFile(line, metaData);
        }catch (Exception e){
            Assert.assertNull(resultFromService);
            Assert.fail();
            e.printStackTrace();
        }
    }

    @Test
    public void convertFileForInvalidNumber() {
        fileConverterService = new FileConverterService();
        String line = "1988-11-28Bob           Smith          abc";
        Metadata metaData = new Metadata(metadataFilePath);
        String resultFromService = null;
        try {
            resultFromService = fileConverterService.convertFile(line, metaData);
        }catch (Exception e){
            Assert.assertNull(resultFromService);
            Assert.fail();
            e.printStackTrace();
        }
    }

    @Test
    public void convertFileForStringWithSpecialChars() {
        fileConverterService = new FileConverterService();
        String line = "1970-01-01Jo,hn\"         Smith          81.5 ";
        String expectedOutput = "01/01/1970,\"Jo,hn\"\",Smith,81.5";
        Metadata metaData = new Metadata(metadataFilePath);
        String resultFromService = null;
        try {
            resultFromService = fileConverterService.convertFile(line, metaData);
            Assert.assertEquals(expectedOutput, resultFromService);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}