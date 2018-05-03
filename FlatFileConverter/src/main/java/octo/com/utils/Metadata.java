package octo.com.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Metadata {

    public enum DataType {
        DATE, NUMERIC, STRING
    }

    public class MetadataFields
    {
        private String columnName;
        private DataType columnType;
        public int columnSize;

        public String getColumnName() {
            return columnName;
        }

        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }

        public int getColumnSize() {
            return columnSize;
        }

        public void setColumnSize(int columnSize) {
            this.columnSize = columnSize;
        }

        public DataType getColumnType() {
            return columnType;
        }

        public void setColumnType(DataType columnType) {
            this.columnType = columnType;
        }
    }

    private List<MetadataFields> metaData = new ArrayList<MetadataFields>();

    public Metadata(String file) {
       try
       {
           /* Can also be done using apache.commons.csv.CSVRecord*/
           List<String> data = Files.readAllLines(Paths.get(file), StandardCharsets.UTF_8);
           for (String nextLine : data)
           {
               String[] fields = nextLine.split(",");
               MetadataFields fieldMetaData = new MetadataFields();
               fieldMetaData.setColumnName(fields[0]);
               fieldMetaData.setColumnSize(Integer.valueOf(fields[1]));
               fieldMetaData.setColumnType(DataType.valueOf(fields[2].toUpperCase()));
               metaData.add(fieldMetaData);
           }
       }
       catch(IOException exception){
            exception.printStackTrace();
            System.out.println("Error occurred during Metadata File loading");
       }
    }

    public List<MetadataFields> getMetaData() {
        return metaData;
    }

    public String convertToCSV() {
        return metaData.stream().map(MetadataFields::getColumnName).collect( Collectors.joining( "," ) );
    }

    public int getTotalFieldsSize(){
        int length = 0;

        for (MetadataFields column : metaData) {
            length += column.columnSize;
        }

        return length;
    }

}
