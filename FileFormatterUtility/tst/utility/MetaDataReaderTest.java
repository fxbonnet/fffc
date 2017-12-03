package utility;

import com.project.utility.FileFormatter;
import com.project.utility.FileStructure;
import com.project.utility.MetaDataReader;
import com.project.utility.exception.FileFormatterException;
import com.project.utility.exception.IncorrectDataFormatException;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by rssridh on 12/4/17.
 */
public class MetaDataReaderTest {
    @Test(expected = FileFormatterException.class)
    public void testMetaDataReaderWithoutAFile() throws FileFormatterException {
        MetaDataReader metaDataReader = new MetaDataReader("nofile");
        metaDataReader.getFileStructure();
    }

    @Test(expected = FileFormatterException.class)
    public void testMetaDataReaderWithoutAnEmptyFile() throws FileFormatterException {
        MetaDataReader metaDataReader = new MetaDataReader("MetadataEmpty.txt");
        metaDataReader.getFileStructure();
    }

    @Test
    public void testMetaDataReader() throws FileFormatterException {
        MetaDataReader metaDataReader = new MetaDataReader("Metadata.txt");
        ArrayList<FileStructure> data =  metaDataReader.getFileStructure();
        Assert.assertTrue(data.size() == 4);
        Assert.assertEquals("Birth date", data.get(0).getName());
        Assert.assertEquals("date", data.get(0).getType());
        Assert.assertEquals(10, data.get(0).getLength());
    }

    @Test
    public void testGetTitle() throws FileFormatterException {
        MetaDataReader metaDataReader = new MetaDataReader("Metadata.txt");
        ArrayList<FileStructure> data =  metaDataReader.getFileStructure();
        String title = metaDataReader.getTitleRow(data);
        Assert.assertTrue(title.length() != 0);
        Assert.assertEquals("Birth date,First name,Last name,Weight", title);
    }

}
