package com.an.service;

import com.an.model.Column;

import java.io.File;
import java.util.List;

public interface FileProcessorService {

    public void processFile(File sourceFile,File metadataFile,String outputFile);

    public List<Column> readMetadata(File metadatafile) throws  Exception;

    public String[] generateHeader(List<Column> columnList);

    public void writeCSVFile(String[] line,String outputFile) throws  Exception;

    public int getLineSize(List<Column> columnList);

    public List<String> processFileLine(String line,List<Column> columnList,int expectedLength) throws Exception;
}
