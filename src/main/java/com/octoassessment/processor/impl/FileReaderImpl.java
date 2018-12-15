package com.octoassessment.processor.impl;

import com.octoassessment.exception.FixedFileFormatConversionException;
import com.octoassessment.processor.FileReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class FileReaderImpl implements FileReader<List<String>> {

    private String path;
    private int batchSize = 100;
    private Scanner sc;
    private InputStream inputStream;

    @Override
    public List<String> read() throws IOException, FixedFileFormatConversionException {
        List<String> lines =  getNextBatch(sc);
        return lines;
    }

    public  void closeResouces() throws FixedFileFormatConversionException {
        try {
            if (inputStream != null) {
                inputStream.close();
            }
            Optional.ofNullable(sc).ifPresent(t -> sc.close());
        }catch (Exception ex){
            throw new FixedFileFormatConversionException("Error closing input resources");
        }
    }

    private List<String> getNextBatch(Scanner sc) throws IOException{
        int batchLineCount = 0;
        List<String> lines = new ArrayList<>();
            while (sc.hasNextLine() && batchLineCount < batchSize) {
                String line = sc.nextLine();
                lines.add(line);
                batchLineCount++;
            }
            if (sc.ioException() != null) {
                throw sc.ioException();
            }
            return lines;
    }

    @Override
    public boolean hasMoreToRead() throws FixedFileFormatConversionException {
        try {
            return sc.hasNextLine();
        }catch (Exception ex){
            throw new FixedFileFormatConversionException("Error fetching more rows");
        }
    }

    public void initialize() throws FixedFileFormatConversionException {
        try{
            if (sc == null) {
                inputStream = new FileInputStream(path);
                sc = new Scanner(inputStream, "UTF-8");
            }
        }catch(Exception ex){
            throw new FixedFileFormatConversionException("Error initializing read resources");
        }
    }

    @Override
    public void setSourcePath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }
}
