package octo.service;

import octo.exception.OutputFileException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by anjana on 27/05/18.
 */
public class OutputFileWriterTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    FffcService ffscService;
    List<String> testData;

    @Before
    public void initialize() {
        ffscService = new FffcServiceImpl();
        try {
            testData = Files.readAllLines(Paths.get("files/test/output.csv"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testWriteToCsv() {
        String testOutputFileLocation = "files/test/outputFile.csv";
        try {
            ffscService.writeToCsv(testOutputFileLocation, testData);
        } catch (OutputFileException e) {
            Assert.fail("Unable to create the csv file");
        }
    }

    @Test
    public void testInvalidFileLocation() throws OutputFileException {
        String testOutputFileLocation = "file/test/outputInvalid";
        thrown.expect(OutputFileException.class);
        thrown.expectMessage("Problem creating output file");
        ffscService.writeToCsv(testOutputFileLocation, testData);


    }
}
