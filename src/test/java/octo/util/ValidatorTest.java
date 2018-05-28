package octo.util;

import octo.exception.MetadataFileException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Created by anjana on 27/05/18.
 */
public class ValidatorTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testValidateMetadataFile() {
        String[] testData = {"Birth date", "10", "date"};
        try {
            Validator.validateMetadataFile(testData);
        } catch (MetadataFileException e) {
            Assert.fail("No exception should occur for a va;lid input");
        }
    }

    @Test
    public void testSpecialCharacter() {
        String[] testData = {"@#Birth date##", "10", "date"};
        try {
            Validator.validateMetadataFile(testData);
        } catch (MetadataFileException e) {
            Assert.fail("No exception should occur for a va;lid input");
        }
    }

    @Test
    public void testInvalidColumnLength() throws MetadataFileException{
        String[] testData = {"Birth date", "date", "10"};
        thrown.expect(MetadataFileException.class);
        thrown.expectMessage("Missing column length information");
        Validator.validateMetadataFile(testData);

    }

    @Test
    public void testBlankColumnName() throws MetadataFileException{
        String[] blankColumnName = {" ", "10", "date"};
        thrown.expect(MetadataFileException.class);
        thrown.expectMessage("Missing column name information");
        Validator.validateMetadataFile(blankColumnName);
    }

    @Test
    public void testNumericColumnName() throws MetadataFileException {
        String[] numericColumnName = {"123", "10", "string"};
        thrown.expect(MetadataFileException.class);
        thrown.expectMessage("Missing column name information");
        Validator.validateMetadataFile(numericColumnName);
    }

    @Test
    public void testInvalidColumnType() throws MetadataFileException {
        String[] numericColumnName = {"First", "10", "efg"};
        thrown.expect(MetadataFileException.class);
        thrown.expectMessage("Invalid column type");
        Validator.validateMetadataFile(numericColumnName);
    }

    @Test
    public void testMissingColumnType() throws MetadataFileException {
        String[] numericColumnName = {"First", "10","   "};
        thrown.expect(MetadataFileException.class);
        thrown.expectMessage("Missing column type information");
        Validator.validateMetadataFile(numericColumnName);
    }

    @Test
    public void testMissingColumn() throws MetadataFileException {
        String[] numericColumnName = {"First", "10",};
        thrown.expect(MetadataFileException.class);
        thrown.expectMessage("Missing column metadata information");
        Validator.validateMetadataFile(numericColumnName);
    }
}
