package client;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class FlatFileConverterTest {
    String inputFilePath = "src\\main\\test-data\\input.dat";
    String metadataFilePath = "src\\main\\test-data\\metadata.csv";
    String outputFilePath = "src\\main\\test-data\\output.csv";
    String testFilePath = "src\\main\\test-data\\outputTest.csv";

    @Test
    public void main() {
        FlatFileConverterTest test = new FlatFileConverterTest();
        try {
            test.convert();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void convert() throws IOException{
        FlatFileConverter.main(new String[] {metadataFilePath,inputFilePath,outputFilePath});
        Assert.assertTrue(FileUtils.contentEquals(new File(outputFilePath), new File(testFilePath)));
    }
}