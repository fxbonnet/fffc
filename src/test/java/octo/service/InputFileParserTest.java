package octo.service;

import octo.exception.InputFileException;
import octo.model.ColumnMetadata;
import octo.model.ColumnType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anjana on 27/05/18.
 */
public class InputFileParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    FffcService ffscService;
    List<ColumnMetadata> columnMetadataList;
    List<String> expectedOutput;

    @Before
    public void initialize() {
        ffscService = new FffcServiceImpl();
        columnMetadataList = new ArrayList<>();
        ColumnMetadata metadataBirthDate = new ColumnMetadata("Birth date", 10, ColumnType.DATE);
        columnMetadataList.add(metadataBirthDate);
        ColumnMetadata metadataFirstName = new ColumnMetadata("First name", 15, ColumnType.STRING);
        columnMetadataList.add(metadataFirstName);
        ColumnMetadata metadataLastName = new ColumnMetadata("Last name", 15, ColumnType.STRING);
        columnMetadataList.add(metadataLastName);
        ColumnMetadata metadataWeight = new ColumnMetadata("Weight", 5, ColumnType.NUMERIC);
        columnMetadataList.add(metadataWeight);

        try {
            expectedOutput = Files.readAllLines(Paths.get("files/test/output.csv"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testReadInputFile() {
        String testInputFileLocation = "files/test/input";
        try {
            List<String> formattedFile = ffscService.readInputFile(testInputFileLocation, columnMetadataList);
            Assert.assertEquals(expectedOutput, formattedFile);
        } catch (InputFileException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testReadInvalidInputFile() throws InputFileException {
        String testInputFileLocation = "files/test/input_invalid";
        thrown.expect(InputFileException.class);
        thrown.expectMessage("Problem parsing input file");
        ffscService.readInputFile(testInputFileLocation, columnMetadataList);
    }

    @Test
    public void testSpecialCharacterInputFile() throws InputFileException,IOException {
        String testInputFileLocation = "files/test/input_special_character";
        List<String> formattedFile = ffscService.readInputFile(testInputFileLocation, columnMetadataList);
        List<String> expected=Files.readAllLines(Paths.get("files/test/output_special_character.csv"));
        Assert.assertEquals(expected, formattedFile);

    }
}
