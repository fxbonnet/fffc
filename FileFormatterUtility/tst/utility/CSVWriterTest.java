package utility;

import com.project.utility.CSVWriter;
import com.project.utility.FileConverter;
import com.project.utility.exception.FileFormatterException;
import com.project.utility.exception.IncorrectDataFormatException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by rssridh on 12/4/17.
 */
public class CSVWriterTest {
    @Test
    public void testFileConverterConvert() throws FileFormatterException, IncorrectDataFormatException {
        CSVWriter csvWriter = new CSVWriter("testfile");
        csvWriter.writeLine("one, two,three,four");
        csvWriter.closeFile();
    }

    @Test
    public void testGetDelimiters() throws FileFormatterException, IncorrectDataFormatException {
        CSVWriter csvWriter = new CSVWriter("testfile");
        csvWriter.setDelimiter();
        Assert.assertEquals(",", csvWriter.getDelimiter());
    }
}
