package com.octoassessment.processor.impl;

import com.octoassessment.exception.FixedFileFormatConversionException;
import com.octoassessment.model.Line;
import com.octoassessment.model.Metadata;
import com.octoassessment.processor.FileWriter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class FileWriterImpl implements FileWriter<List<Line>,Metadata> {

    BufferedWriter bufferedWriter;
    Boolean initialized = Boolean.FALSE;

    @Override
    public void write(List<Line> linesToWrite, String destinationFilePath, Metadata metadata) throws FixedFileFormatConversionException {
        try {
            List<String> output = linesToWrite.stream()
                    .map(t -> t.getColumns().stream().map(v -> ((String)v.getValue()))
                            .collect(Collectors.joining(","))).collect(Collectors.toList());
            for(String line : output){
                bufferedWriter.write(line+"\n");
            }
        } catch (IOException e) {
            throw new FixedFileFormatConversionException("Error writing to file. Message:"+e.getMessage());
        }

    }

    public void initialize(String destinationPath,Metadata metadata) throws FixedFileFormatConversionException {
        try {
            if (Files.exists(Paths.get(destinationPath))) {
                Files.delete(Paths.get(destinationPath));
            }
            bufferedWriter = new BufferedWriter(new java.io.FileWriter(destinationPath, true));
            bufferedWriter.write(metadata.getColumnMetaData().stream().map(t -> t.getColumnName()).collect(Collectors.joining(",")) + "\n");
            initialized = Boolean.TRUE;
        }catch(Exception ex){
            throw new FixedFileFormatConversionException("Error initializing write resources");
        }
    }

    public void closeResources() throws FixedFileFormatConversionException {
        try {
            if(bufferedWriter != null)
                bufferedWriter.close();
        } catch (IOException e) {
            throw new FixedFileFormatConversionException(e);
        }

    }
}
