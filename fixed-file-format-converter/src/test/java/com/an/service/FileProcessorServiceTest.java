package com.an.service;

import com.an.model.Column;
import com.an.model.ColumnType;
import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class FileProcessorServiceTest {

    private File metadatafile;
    private File sourceFile;
    private final String outputFilePath = getClass().getClassLoader().getResource("files/Output.csv").getPath();

    @Before
    public void setup(){

        metadatafile = getFileFromResources("files/Metadata.txt");
        sourceFile = getFileFromResources("files/Source.txt");

    }

    private File getFileFromResources(String fileName){
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource(fileName).getFile());
    }

    @Test
    public void testProcessMetadata_Success() throws  Exception{

        FileProcessorService fileProcessorService = new FileProcessorServiceImpl();
        List<Column> columnList = fileProcessorService.readMetadata(metadatafile);
        Assert.assertEquals("Birth date",columnList.get(0).getName());

        //System.out.println(fileProcessorService.getLineSize(columnList));
        Assert.assertEquals(45,fileProcessorService.getLineSize(columnList));

        String[] header = fileProcessorService.generateHeader(columnList);
        Assert.assertEquals(4,header.length);

        fileProcessorService.writeCSVFile(header,outputFilePath);

        String rawValue = "1970-01-01John           Smith           81.5";
        List<String> newLine = fileProcessorService.processFileLine(rawValue,columnList,45);
        Assert.assertNotNull(newLine);

    }

    @Test
    public void testProcessFile_Success() throws  Exception{
        FileProcessorService fileProcessorService = new FileProcessorServiceImpl();
        fileProcessorService.processFile(sourceFile,metadatafile,outputFilePath);

    }


}
