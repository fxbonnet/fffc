package octo.service;

import octo.exception.MetadataFileException;
import octo.model.ColumnMetadata;
import octo.model.ColumnType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anjana on 27/05/18.
 */
public class ColumnMetadataParserTest {

    FffcService ffscService;
    List<ColumnMetadata> columnMetadataListExpected;

    @Before
    public void initialize(){
        ffscService = new FffcServiceImpl();
        columnMetadataListExpected=new ArrayList<>();
        ColumnMetadata metadataBirthDate=new ColumnMetadata("Birth date",10, ColumnType.DATE);
        columnMetadataListExpected.add(metadataBirthDate);
        ColumnMetadata metadataFirstName=new ColumnMetadata("First name",15, ColumnType.STRING);
        columnMetadataListExpected.add(metadataFirstName);
        ColumnMetadata metadataLastName=new ColumnMetadata("Last name",15, ColumnType.STRING);
        columnMetadataListExpected.add(metadataLastName);
        ColumnMetadata metadataWeight=new ColumnMetadata("Weight",5, ColumnType.NUMERIC);
        columnMetadataListExpected.add(metadataWeight);
    }

    @Test
    public void testGetColumnMetadata(){
        String testMetadtaFileLocation = "files/test/metadata.csv";
        try {
            List<ColumnMetadata> columnMetadataList=ffscService.getColumnMetadata(testMetadtaFileLocation);
            Assert.assertEquals("Details parsed from the metadata file should be same as the file contents",columnMetadataListExpected,columnMetadataList);

        } catch (MetadataFileException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = MetadataFileException.class)
    public void testInvalidColumnMetadata() throws MetadataFileException{
        String testMetadtaFileLocation = "files/test/metadata_invalid.csv";
        ffscService.getColumnMetadata(testMetadtaFileLocation);
    }


}
