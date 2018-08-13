package com.octo.fffc.utils;

import com.octo.fffc.exceptions.FileLocationNotFoundException;
import com.octo.fffc.exceptions.MetadataFileException;
import com.octo.fffc.model.Metadata;
import com.octo.fffc.model.MetadataColumn;
import com.octo.fffc.model.XlRow;
import com.octo.fffc.model.XlTable;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * @author alanterriaga
 * @project FFFC
 */
@RunWith(MockitoJUnitRunner.class)
public class ConvertersTest {

    @InjectMocks
    Converters converters;

    private final String metadata_file = "metadata.txt";
    private final String metadata_empty = "metadata_empty.txt";
    private final String metadata_wrongFormat = "metadata_wrongFormat.txt";
    private final String dataFile_empty = "dataFile_empty.txt";
    private final String dataFile_wrongSpec = "dataFile_wrongSpec.txt";
    private final String dataFile_wrongLineSpec = "dataFile_wrongLineSpec.txt";
    private final String dataFile = "dataFile.txt";

    @Before
    public void setUp(){

        ReflectionTestUtils.setField(converters, "fileLocation", "/Users/alanterriaga/IdeaProjects/FFFC/fffcFiles");
    }


    /**
     * Scenario: File Location not found
     *
     * expected: throws FileLocationNotFoundException
     *
     * @throws Exception
     */
    @Test(expected = FileLocationNotFoundException.class)
    public void test_convertInputToModel_noFileLocationSet() throws Exception {

        ReflectionTestUtils.setField(converters, "fileLocation", "");

        Metadata metadata = converters.convertInputToMetadata(metadata_file);
    }

    /**
     * Scenario: Metadata file not found
     *
     * expected: throws FileNotFoundException
     *
     * @throws Exception
     */
    @Test(expected = FileNotFoundException.class)
    public void test_convertInputToModel_metaDataFileNotFound() throws Exception {

        Metadata metadata = converters.convertInputToMetadata("anyFile");
    }

    /**
     * Scenario: Meta data file has a wrong expected format
     *
     * Expected: Throws MetadataFileException
     *
     * @throws Exception
     */
    @Test(expected = MetadataFileException.class)
    public void test_convertInputToModel_metaDataWrongFormat() throws Exception {

        Metadata metadata = converters.convertInputToMetadata(metadata_wrongFormat);
    }

    /**
     * Scenario: Meta data file is empty
     *
     * Result: Metadata object is converted but empty
     * @throws Exception
     */
    @Test
    public void test_convertInputToModel_metaDataEmpty() throws Exception {

        Metadata metadata = converters.convertInputToMetadata(metadata_empty);

        Assert.assertTrue(CollectionUtils.isEmpty(metadata.getMetadataColumnList()));
    }

    /**
     * Birth date,10,date
     * First name,15,string
     * Last name,15,string
     * Weight,5,numeric
     *
     * Result: Metadata object converted
     *
     * @throws Exception
     */
    @Test
    public void test_convertInputToModel_success() throws Exception {

        Metadata metadata = converters.convertInputToMetadata(metadata_file);

        Assert.assertTrue(CollectionUtils.isNotEmpty(metadata.getMetadataColumnList()));

        List<MetadataColumn> list = metadata.getMetadataColumnList();

        int cont = 0;
        for(MetadataColumn column: metadata.getMetadataColumnList() ) {

            switch (cont) {
                case 0:
                    Assert.assertEquals("Birth date", column.getHeader());
                    Assert.assertEquals(10, column.getSize());
                    Assert.assertEquals(MetadataColumn.ColumnFormat.DATE, column.getColumnFormat());
                    break;
                case 1:
                    Assert.assertEquals("First name", column.getHeader());
                    Assert.assertEquals(15, column.getSize());
                    Assert.assertEquals(MetadataColumn.ColumnFormat.TEXT, column.getColumnFormat());
                    break;
                case 2:
                    Assert.assertEquals("Last name", column.getHeader());
                    Assert.assertEquals(15, column.getSize());
                    Assert.assertEquals(MetadataColumn.ColumnFormat.TEXT, column.getColumnFormat());
                    break;
                case 3:
                    Assert.assertEquals("Weight", column.getHeader());
                    Assert.assertEquals(5, column.getSize());
                    Assert.assertEquals(MetadataColumn.ColumnFormat.NUMERIC, column.getColumnFormat());
                    break;
            }

            cont++;
        }
    }

    /**
     * Scenario: File Location not found
     *
     * Result: throws FileLocationNotFoundException
     * @throws Exception
     */
    @Test(expected = FileLocationNotFoundException.class)
    public void test_convertInputToXlTable_propsNotSet() throws Exception {

        ReflectionTestUtils.setField(converters,"fileLocation", "");

        XlTable xlTable = converters.convertInputToXlTable( new Metadata(), "anyInput.txt");
    }

    /**
     * Scenario: Data File Not Found
     *
     * Result: throws FileNotFoundException
     * @throws Exception
     */
    @Test(expected = FileNotFoundException.class)
    public void test_convertInputToXlTable_DataFileNotFound() throws Exception {

        Metadata metadata = converters.convertInputToMetadata(metadata_file);

        XlTable xlTable = converters.convertInputToXlTable( metadata, "anyInput.txt");
    }

    /**
     * Scenario: Input file is empty
     *
     * Result: XlTable object is converted but with empty attributes
     * @throws Exception
     */
    @Test
    public void test_convertInputToXlTable_InputFileEmpty() throws Exception {

        Metadata metadata = converters.convertInputToMetadata(metadata_file);

        XlTable xlTable = converters.convertInputToXlTable( metadata, dataFile_empty);

        Assert.assertNotNull(xlTable);
        Assert.assertTrue(CollectionUtils.isNotEmpty(xlTable.getHeaders()));
        Assert.assertTrue(CollectionUtils.isEmpty(xlTable.getRows()));
    }

    /**
     * Scenario: Data file has wrong specification
     *
     * Result: XlTable object is converted but with empty attributes
     * @throws Exception
     */
    @Test
    public void test_convertInputToXlTable_allLinesWrongSpecification() throws Exception {

        Metadata metadata = converters.convertInputToMetadata(metadata_file);

        XlTable xlTable = converters.convertInputToXlTable( metadata, dataFile_wrongSpec);

        Assert.assertNotNull(xlTable);
        Assert.assertTrue(CollectionUtils.isNotEmpty(xlTable.getHeaders()));
        Assert.assertTrue(CollectionUtils.isEmpty(xlTable.getRows()));
    }

    /**
     * Scenario: Data file has some lines with wrong specification
     *
     * Result: XlTable converted only with right lines
     * @throws Exception
     */
    @Test
    public void test_convertInputToXlTable_someLinesWrongSpecification() throws Exception {

        Metadata metadata = converters.convertInputToMetadata(metadata_file);

        XlTable xlTable = converters.convertInputToXlTable( metadata, dataFile_wrongLineSpec);

        Assert.assertNotNull(xlTable);
        Assert.assertTrue(CollectionUtils.isNotEmpty(xlTable.getHeaders()));
        Assert.assertTrue(xlTable.getRows().size() == 1);
    }

    /**
     * Scenario: Data has the right specification
     *
     * First 8 lines:
     * 1970-01-01John           Smith           81.5
     * 1975-01-31Jane           Doe             61.1
     * 1970-01-01John           WithHugeLastNam 81.5
     * 1970-01-01 John          WithHugeLastNam 81.5
     * 1975-01-31  Jane         Micheal         61.1
     * 1988-11-28   Bob         Stevie          99.4
     * 1988-11-28Bob            Big$5%6        102.4
     * 1975-01-31Jane           Doe with ,,     61.1
     *
     * Result: XlTable converted only with right lines
     * @throws Exception
     */
    @Test
    public void test_convertInputToXlTable_success() throws Exception {

        Metadata metadata = converters.convertInputToMetadata(metadata_file);

        XlTable xlTable = converters.convertInputToXlTable( metadata, dataFile);

        Assert.assertNotNull(xlTable);
        Assert.assertTrue(CollectionUtils.isNotEmpty(xlTable.getHeaders()));
        Assert.assertTrue(xlTable.getRows().size() == 60);

        int cont = 0;
        for(XlRow row: xlTable.getRows() ) {

            if(cont>5){
                break;
            }

            String date = row.getValues().get(0);
            String firstName = row.getValues().get(1);
            String lastName = row.getValues().get(2);
            String weight = row.getValues().get(3);

            switch (cont) {
                case 0:
                    Assert.assertEquals("1970-01-01", date);
                    Assert.assertEquals("John", firstName);
                    Assert.assertEquals("Smith", lastName);
                    Assert.assertEquals("81.5", weight);
                    break;
                case 1:
                    Assert.assertEquals("1975-01-31", date);
                    Assert.assertEquals("Jane", firstName);
                    Assert.assertEquals("Doe", lastName);
                    Assert.assertEquals("61.1", weight);
                    break;
                case 2:
                    Assert.assertEquals("1970-01-01", date);
                    Assert.assertEquals("John", firstName);
                    Assert.assertEquals("WithHugeLastNam", lastName);
                    Assert.assertEquals("81.5", weight);
                    break;
                case 3:
                    Assert.assertEquals("1970-01-01", date);
                    Assert.assertEquals("John", firstName);
                    Assert.assertEquals("WithHugeLastNam", lastName);
                    Assert.assertEquals("81.5", weight);
                    break;
                case 4:
                    Assert.assertEquals("1975-01-31", date);
                    Assert.assertEquals("Jane", firstName);
                    Assert.assertEquals("Micheal", lastName);
                    Assert.assertEquals("61.1", weight);
                    break;
            }

            cont++;
        }
    }


    /**
     * Scenario: Data has the right specification
     *
     * 1988-11-28   Bob         Stevie          99.4
     * 1988-11-28Bob            Big$5%6        102.4
     * 1975-01-31Jane           Doe with ,,     61.1
     *
     * Result: XlTable converted only with right lines
     * @throws Exception
     */
    @Test
    public void test_convertInputToXlTable_success_commasAndCaracters() throws Exception {

        Metadata metadata = converters.convertInputToMetadata(metadata_file);

        XlTable xlTable = converters.convertInputToXlTable( metadata, dataFile);

        Assert.assertNotNull(xlTable);
        Assert.assertTrue(CollectionUtils.isNotEmpty(xlTable.getHeaders()));
        Assert.assertTrue(xlTable.getRows().size() == 60);

        int cont = 0;
        for(XlRow row: xlTable.getRows() ) {

            if(cont>9){
                break;
            }

            String date = row.getValues().get(0);
            String firstName = row.getValues().get(1);
            String lastName = row.getValues().get(2);
            String weight = row.getValues().get(3);

            switch (cont) {
                case 5:
                    Assert.assertEquals("1988-11-28", date);
                    Assert.assertEquals("Bob", firstName);
                    Assert.assertEquals("Stevie", lastName);
                    Assert.assertEquals("99.4", weight);
                    break;
                case 6:
                    Assert.assertEquals("1988-11-28", date);
                    Assert.assertEquals("Bob", firstName);
                    Assert.assertEquals("Big$5%6", lastName);
                    Assert.assertEquals("102.4", weight);
                    break;
                case 7:
                    Assert.assertEquals("1975-01-31", date);
                    Assert.assertEquals("Jane", firstName);
                    Assert.assertEquals("'Doe with ,,'", lastName);
                    Assert.assertEquals("61.1", weight);
                    break;
            }

            cont++;
        }
    }


    /**
     * Scenario: File Location not found
     *
     * Result: throws FileLocationNotFoundException
     * @throws Exception
     */
    @Test(expected = FileLocationNotFoundException.class)
    public void test_saveFile_locationNotFound() throws Exception {

        ReflectionTestUtils.setField(converters,"fileLocation", "");

        converters.saveFile(new HSSFWorkbook());
    }

}
