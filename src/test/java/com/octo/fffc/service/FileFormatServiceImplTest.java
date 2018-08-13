package com.octo.fffc.service;

import com.octo.fffc.exceptions.InputFileException;
import com.octo.fffc.exceptions.MetadataFileException;
import com.octo.fffc.helper.FileWriterHelper;
import com.octo.fffc.model.*;
import com.octo.fffc.service.impl.FileFormatServiceImpl;
import com.octo.fffc.utils.Converters;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * @author alanterriaga
 * @project FFFC
 */
@RunWith(MockitoJUnitRunner.class)
public class FileFormatServiceImplTest {

    @Mock
    Converters converters;

    @Mock
    FileWriterHelper fileWriterHelper;

    @InjectMocks
    FileFormatServiceImpl fileFormatService;

    private final String metadata_file = "metadata.txt";
    private final String dataFile = "dataFile.txt";

    /**
     * Scenario: Metadata object not generated
     * Expected: Does not create CSV file = MetadataFileException
     *
     * @throws Exception
     */
    @Test(expected = MetadataFileException.class)
    public void test_formatAndCreateFile_noMetaData() throws Exception {

        InputRequestDto inputRequestDto = new InputRequestDto();
        inputRequestDto.setMetaDataFileName(metadata_file);

        when(converters.convertInputToMetadata(metadata_file)).thenReturn(null);

        boolean result = fileFormatService.formatAndCreateFile(inputRequestDto);

        Assert.assertFalse(result);
    }

    /**
     * Scenario: XlTable object not generated with information
     * Expected: Does not create CSV file
     * InputFileException
     *
     * @throws Exception
     */
    @Test(expected = InputFileException.class)
    public void test_formatAndCreateFile_noXlTable() throws Exception {

        InputRequestDto inputRequestDto = new InputRequestDto();
        inputRequestDto.setMetaDataFileName(metadata_file);
        inputRequestDto.setDataFileName(dataFile);

        MetadataColumn metadataColumn = new MetadataColumn("header", 15, "text");

        Metadata metadata = new Metadata();
        metadata.setMetadataColumnList(Arrays.asList(metadataColumn));

        when(converters.convertInputToMetadata(metadata_file)).thenReturn(metadata);

        when(converters.convertInputToXlTable(metadata, dataFile)).thenReturn(null);

        boolean result = fileFormatService.formatAndCreateFile(inputRequestDto);
    }

    /**
     * Scenario: Apache POI HSSFWorkbook not created
     * Expected: Does not create CSV file
     *
     * @throws Exception
     */
    @Test
    public void test_formatAndCreateFile_workbookNotCreated() throws Exception {

        InputRequestDto inputRequestDto = new InputRequestDto();
        inputRequestDto.setMetaDataFileName(metadata_file);
        inputRequestDto.setDataFileName(dataFile);

        MetadataColumn metadataColumn = new MetadataColumn("header", 15, "text");

        Metadata metadata = new Metadata();
        metadata.setMetadataColumnList(Arrays.asList(metadataColumn));

        when(converters.convertInputToMetadata(metadata_file)).thenReturn(metadata);

        XlRow row = new XlRow();
        XlTable xlTable = new XlTable();
        xlTable.setRows(Arrays.asList(row));

        when(converters.convertInputToXlTable(metadata, dataFile)).thenReturn(xlTable);

        when(fileWriterHelper.createWorkbook(xlTable)).thenReturn(null);

        boolean result = fileFormatService.formatAndCreateFile(inputRequestDto);

        Assert.assertFalse(result);
    }

    /**
     * Scenario: Apache POI HSSFWorkbook created
     * Expected: "Saves" CSV file
     *
     * @throws Exception
     */
    @Test
    public void test_formatAndCreateFile_success() throws Exception {

        InputRequestDto inputRequestDto = new InputRequestDto();
        inputRequestDto.setMetaDataFileName(metadata_file);
        inputRequestDto.setDataFileName(dataFile);

        MetadataColumn metadataColumn = new MetadataColumn("header", 15, "text");

        Metadata metadata = new Metadata();
        metadata.setMetadataColumnList(Arrays.asList(metadataColumn));

        when(converters.convertInputToMetadata(metadata_file)).thenReturn(metadata);

        XlRow row = new XlRow();
        XlTable xlTable = new XlTable();
        xlTable.setRows(Arrays.asList(row));

        when(converters.convertInputToXlTable(metadata, dataFile)).thenReturn(xlTable);

        when(fileWriterHelper.createWorkbook(xlTable)).thenReturn(new HSSFWorkbook());

        doNothing().when(converters).saveFile(any());

        boolean result = fileFormatService.formatAndCreateFile(inputRequestDto);

        Assert.assertTrue(result);
    }

}
