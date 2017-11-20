package com.an.service;

import com.an.model.Column;
import com.an.model.ColumnType;
import com.an.util.DateFormatter;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileProcessorServiceImpl implements FileProcessorService {

    private static final Logger LOGGER = Logger.getLogger(FileProcessorServiceImpl.class.getName());

    //to process very large file sizes (in GBs) , iterate through one line at a time to avoid OutOfMemoryError
    public void processFile(File sourceFile,File metadataFile,String outputFile){
        try{

            //generate column information
            List<Column> columnList = readMetadata(metadataFile);

            //generate header row

            String[] header = generateHeader(columnList);

            //write header row to outputfile
            writeCSVFile(header,outputFile);

            //get line size
            int expectedLength = getLineSize(columnList);

            //Read one line at a time
            LineIterator it = FileUtils.lineIterator(sourceFile,"UTF-8");
            try{
                while(it.hasNext()){
                    String line = it.nextLine();
                    List<String> newLine = processFileLine(line,columnList,expectedLength);
                    writeCSVFile(newLine.stream().toArray(String[]::new),outputFile);

                }
            }finally {
                LineIterator.closeQuietly(it);
            }

        }catch (Exception e){
            LOGGER.log(Level.SEVERE,e.getMessage(),e);
        }
    }

    public List<String> processFileLine(String line,List<Column> columnList,int expectedLength) throws Exception {

         if(line.length() < expectedLength) {
             LOGGER.log(Level.SEVERE, "throwing away line with length less than expected , LINE ["+line+"]");
             return null;
         }

        List<String> newLine = new ArrayList<String>();


        for(Column column:columnList){
            String columnValue = line.substring(column.getStartPosition(),column.getEndPosition());
            columnValue = columnValue.trim();
            switch (column.getColumnType()){
                case STRING:
                    newLine.add(columnValue);
                    break;
                case NUMERIC:
                    newLine.add(columnValue);
                    break;
                case DATE:
                    newLine.add( DateFormatter.formatDate(columnValue));
                    break;
                case UNKNOWWN: throw new Exception("Unknown Column Type");
            }
        }

         return newLine;
    }

    public String[] generateHeader(List<Column> columnList){
        String[] header = new String[columnList.size()];
        for(int i=0;i<columnList.size();i++){
            header[i] = columnList.get(i).getName();
        }
        return header;
    }

    public int getLineSize(List<Column> columnList){
        int length = 0;
        length = columnList.stream().map(c -> c.getLength()).reduce(0,(x,y) -> x + y);
        return length;
    }

    public void writeCSVFile(String[] line,String outputFile) throws  Exception{
        CSVWriter writer = new CSVWriter(new FileWriter(outputFile, true));
        writer.writeNext(line);
        writer.close();
    }

    //read metadata information. return a list of Metadata
    public List<Column> readMetadata(File metadatafile) throws Exception{

        List<Column> columnList = new ArrayList<Column>();
        CSVReader csvReader = new CSVReader(new FileReader(metadatafile));

        //Read all rows at once
        List<String[]> allRows = csvReader.readAll();

        int postion = 0;

        //Read CSV line by line and use the string array as you want
        for(String[] row : allRows){
//            System.out.println(row.toString());
//            System.out.println("row[0["+row[0]);

            int startPosition = postion;

            String name = row[0];
            //System.out.println(row[1]);
            int length = Integer.parseInt(row[1]);

            //postion = postion + (length - 1);
            postion = postion + length;
            int endPosition = postion ;
            //postion = postion+1;

            //System.out.println(row[2].toLowerCase());
            String type = row[2].trim().toLowerCase();
            //System.out.println(type.equals("date"));
            //System.out.println(ColumnType.forValue(type.trim()));
            //System.out.println(ColumnType.forValue("date"));
            Column column = new Column(row[0],Integer.parseInt(row[1]),ColumnType.forValue(type),startPosition,endPosition);
            columnList.add(column);
        }
        //System.out.println(columnList);

        return  columnList;

    }

}
