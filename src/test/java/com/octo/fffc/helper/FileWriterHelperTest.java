package com.octo.fffc.helper;



import com.octo.fffc.model.Metadata;
import com.octo.fffc.model.XlTable;
import com.octo.fffc.utils.Converters;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * @author alanterriaga
 * @project FFFC
 */
@RunWith(MockitoJUnitRunner.class)
public class FileWriterHelperTest {

    private final String metadata_file = "metadata.txt";
    private final String metadata_empty = "metadata_empty.txt";
    private final String dataFile = "dataFile.txt";
    private final String dataFile_empty = "dataFile_empty.txt";

    FileWriterHelper fileWriterHelper;
    Converters converters;

    @Before
    public void setUp(){
        fileWriterHelper = new FileWriterHelper();
        converters = new Converters();

        ReflectionTestUtils.setField(converters, "fileLocation", "/Users/alanterriaga/IdeaProjects/FFFC/fffcFiles");
    }

    @Test
    public void test_createWorkbook_noHeader() throws Exception{

        Metadata metadata = converters.convertInputToMetadata(metadata_empty);

        XlTable xlTable = converters.convertInputToXlTable(metadata, dataFile_empty);

        HSSFWorkbook workbook = fileWriterHelper.createWorkbook(xlTable);

        Assert.assertNull(workbook);
    }

    @Test
    public void test_createWorkbook_onlyWithHeader() throws Exception{

        Metadata metadata = converters.convertInputToMetadata(metadata_file);

        XlTable xlTable = converters.convertInputToXlTable(metadata, dataFile_empty);

        HSSFWorkbook workbook = fileWriterHelper.createWorkbook(xlTable);

        Assert.assertNotNull(workbook);
        Assert.assertTrue(workbook.getNumberOfSheets() > 0);
        Assert.assertTrue(workbook.getSheetAt(0).getLastRowNum()== 0);
    }

    @Test
    public void test_createWorkbook_successWithData() throws Exception{

        Metadata metadata = converters.convertInputToMetadata(metadata_file);

        XlTable xlTable = converters.convertInputToXlTable(metadata, dataFile);

        HSSFWorkbook workbook = fileWriterHelper.createWorkbook(xlTable);

        Assert.assertNotNull(workbook);
        Assert.assertTrue(workbook.getNumberOfSheets() > 0);
        Assert.assertTrue(workbook.getSheetAt(0).getLastRowNum()== 60);
    }


    private void saveFile( HSSFWorkbook workbook) throws Exception {

        byte[] byteArray;

        try(ByteArrayOutputStream bos = new ByteArrayOutputStream()){
            workbook.write(bos); // write excel data to a byte array
            byteArray = bos.toByteArray();
        }

        File file = new File("/Users/alanterriaga/IdeaProjects/FFFC/fffcFiles/reportAlan.csv");
        file.createNewFile();

        FileOutputStream fileOutputStream = new FileOutputStream(file, false);
        fileOutputStream.write(byteArray);
        fileOutputStream.close();

    }
}
