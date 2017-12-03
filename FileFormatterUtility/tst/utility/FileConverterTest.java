package utility;

import com.project.utility.exception.FileFormatterException;
import com.project.utility.exception.IncorrectDataFormatException;
import com.project.utility.FileConverter;

import org.junit.Test;
import org.junit.Assert;

/**
 * Created by rssridh on 12/4/17.
 */
public class FileConverterTest {
    @Test
    public void testFormatDataBasedOnStringType() throws IncorrectDataFormatException {
        FileConverter fileConverter = new FileConverter("dummy", "dummy", "dummy");
        Assert.assertEquals("Test string", fileConverter.formatDataBasedOnType("  Test string  ", "string"));
        Assert.assertEquals("TestString", fileConverter.formatDataBasedOnType("TestString", "string"));
        Assert.assertEquals("HELLOWORLD", fileConverter.formatDataBasedOnType("HELLOWORLD  ", "STRING"));
    }

    @Test
    public void testFormatDataBasedOnDateType() throws IncorrectDataFormatException {
        FileConverter fileConverter = new FileConverter("dummy", "dummy", "dummy");
        Assert.assertEquals("31-05-2017", fileConverter.formatDataBasedOnType("2017-05-31", "date"));
        Assert.assertEquals("01-05-2017", fileConverter.formatDataBasedOnType("2017-05-01", "DATE"));
    }

    @Test
    public void testFormatDataBasedOnNumericType() throws IncorrectDataFormatException {
        FileConverter fileConverter = new FileConverter("dummy", "dummy", "dummy");
        Assert.assertEquals("123.21", fileConverter.formatDataBasedOnType("123.21", "numeric"));
        Assert.assertEquals("1234.21", fileConverter.formatDataBasedOnType("1234.21", "NUMERIC"));
    }

    @Test(expected = IncorrectDataFormatException.class)
    public void testFormatDataBasedOnFloatType() throws IncorrectDataFormatException {
        FileConverter fileConverter = new FileConverter("dummy", "dummy", "dummy");
        Assert.assertEquals("123.21", fileConverter.formatDataBasedOnType("123.21", "Float"));
    }

    @Test(expected = FileFormatterException.class)
    public void testFileConverterConvertWithNoFiles() throws FileFormatterException, IncorrectDataFormatException {
        FileConverter fileConverter = new FileConverter("dummy", "dummy", "dummy");
        fileConverter.convert();
    }

    @Test
    public void testFileConverterConvert() throws FileFormatterException, IncorrectDataFormatException {
        FileConverter fileConverter = new FileConverter("InputData.txt", "WriteFile", "Metadata.txt");
        fileConverter.convert();
    }

    @Test(expected = FileFormatterException.class)
    public void testFileConverterConvertMissingMetaData() throws FileFormatterException, IncorrectDataFormatException {
        FileConverter fileConverter = new FileConverter("InputData.txt", "WriteFile", "dummy");
        fileConverter.convert();
    }
}
