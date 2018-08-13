package com.octo.fffc.utils;

import com.octo.fffc.exceptions.FileLocationNotFoundException;
import com.octo.fffc.exceptions.MetadataFileException;
import com.octo.fffc.model.Metadata;
import com.octo.fffc.model.MetadataColumn;
import com.octo.fffc.model.XlRow;
import com.octo.fffc.model.XlTable;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author alanterriaga
 * @project FFFC
 */
@Component
public class Converters {

    Logger logger = LoggerFactory.getLogger(Converters.class);

    @Value("${file.convert.location}")
    private String fileLocation;

    @Value("${file.convert.output.name:output.csv}")
    private String outputFileName;

    /**
     * Convert metaDataFile to Metadata custom object
     *
     * @param metaDataFileName
     * @return
     * @throws Exception
     */
    public Metadata convertInputToMetadata(String metaDataFileName) throws Exception {

        if(StringUtils.isEmpty(fileLocation)){
            logger.error("No file location found, aborting converter");
            throw new FileLocationNotFoundException();
        }

        List<MetadataColumn> metadataColumnList = new ArrayList<>();

        try( BufferedReader bf = new BufferedReader(new FileReader(fileLocation+"/"+metaDataFileName)) ){
            String line;

            while( (line = bf.readLine()) != null){
                metadataColumnList.add(convertToMetadataColumn(line));
            }
        }

        Metadata metadata = new Metadata();
        metadata.setMetadataColumnList(metadataColumnList);

        return metadata;
    }

    private MetadataColumn convertToMetadataColumn(String line) throws MetadataFileException, NumberFormatException {

       List<String> columns = Stream.of(line.split(",")).collect(Collectors.toList());

        if (columns.size() != 3) {
            throw new MetadataFileException();
        }

        return new MetadataColumn(columns.get(0), Integer.parseInt(columns.get(1)), columns.get(2));
    }


    /**
     * Convert input data file to XlTable custom object
     * @param metadata
     * @param inputFileName
     * @return
     * @throws Exception
     */
    public XlTable convertInputToXlTable(Metadata metadata, String inputFileName) throws Exception {

        if(StringUtils.isEmpty(fileLocation)){
            logger.error("No file location found, aborting converter");
            throw new FileLocationNotFoundException();
        }

        XlTable xlTable = new XlTable();
        String line;

        int totalLineLength = metadata.getMetadataColumnList().stream()
                .map(MetadataColumn::getSize)
                .collect(Collectors.summingInt(Integer::intValue));

        try( BufferedReader bf = new BufferedReader(new FileReader(fileLocation+"/"+inputFileName)) ){

            xlTable.setHeaders(metadata.getMetadataColumnList());

            while( (line = bf.readLine()) != null){

                if(totalLineLength != line.length()){
                    logger.warn("Line of input file does not follow metadata specifications, ignoring it;");
                    continue;
                }

                int initialPoint = 0;
                int finalPoint;
                XlRow row = new XlRow();

                for(MetadataColumn metadataColumm : metadata.getMetadataColumnList()){

                    finalPoint = initialPoint + metadataColumm.getSize();

                    String value = org.apache.commons.lang3.StringUtils.substring(line, initialPoint, finalPoint).trim();

                    if(org.apache.commons.lang3.StringUtils.isNotEmpty(value)) {

                        if(org.apache.commons.lang3.StringUtils.contains(value, ",")){
                            value = "'"+value+"'";
                        }

                        row.getValues().add(value);
                    }

                    initialPoint = finalPoint;
                }

                xlTable.getRows().add(row);
            }
        }

        return xlTable;
    }

    /**
     * Save the converted file on specific location
     *
     * @param workbook
     * @throws Exception
     */
    public void saveFile(HSSFWorkbook workbook) throws Exception {

        if(StringUtils.isEmpty(fileLocation)){
            logger.error("No file location found, aborting converter");
            throw new FileLocationNotFoundException();
        }

        byte[] byteArray;

        try(ByteArrayOutputStream bos = new ByteArrayOutputStream()){
            workbook.write(bos);
            byteArray = bos.toByteArray();
        }

        File file = new File(fileLocation+"/"+outputFileName);
        file.createNewFile();

        FileOutputStream fileOutputStream = new FileOutputStream(file, false);
        fileOutputStream.write(byteArray);
        fileOutputStream.close();
    }

}
