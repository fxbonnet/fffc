package org.octo.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MetadataComponents {
    private List<String> fieldNames;
    private List<Integer> fieldsSize;
    private List<String> fieldsType;
    private List<Integer> dateTypeIndex;

    private static Logger logger = LoggerFactory.getLogger(MetadataComponents.class);

    private static final int FIELD_METADATA_SIZE = 3;

    public MetadataComponents() {
        fieldNames = new ArrayList();
        fieldsSize = new ArrayList();
        fieldsType = new ArrayList();
        dateTypeIndex = new ArrayList();
    }

    public String[] getFieldNames() {
        return fieldNames.toArray(new String[fieldNames.size()]);
    }

    public int[] getFieldsSize() {
        return fieldsSize.stream().mapToInt(Integer::intValue).toArray();
    }

    public String[] getFieldsType() {
        return fieldsType.toArray(new String[fieldsType.size()]);
    }

    public int[] getDateTypeIndex() {
        return dateTypeIndex.stream().mapToInt(Integer::intValue).toArray();
    }

    // Reading metadata file and returning MetadataComponent
    public static MetadataComponents getMetadata(String fileName) throws Exception {
        logger.debug("getMetadata called with fileName : " + fileName);
        MetadataComponents metadataComponents = new MetadataComponents();
        try(BufferedReader metadataReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int counter = 0;
            logger.debug("Reading metadata file");
            while ((line = metadataReader.readLine()) != null) {
                metadataComponents.addFiledDetails(line.split(","), counter++);
            }
            if (counter == 0) {
                logger.error("Content issue with Metadata file");
                throw new Exception("Content issue with Metadata file");
            }
        } catch (FileNotFoundException e) {
            logger.error("Metadata file Not Found :" + e.getMessage());
            throw e;
        } catch (IOException e) {
            logger.error("IO Exception occurred while parsing Metadata file: :" + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Exception occurred while parsing Metadata file:" + e.getMessage());
            throw e;
        }
        return metadataComponents;
    }

    private void addFiledDetails(String[] fieldDetails, int counter) throws Exception {
        logger.debug("addFiledDetails called  with fieldDetails :" + fieldDetails + "and counter :" + counter);
        try {
            if(fieldDetails.length == FIELD_METADATA_SIZE) {
                if (fieldNames != null && fieldsSize != null && fieldsType != null) {
                    fieldNames.add(fieldDetails[0]);
                    fieldsSize.add(Integer.parseInt(fieldDetails[1]));
                    fieldsType.add(fieldDetails[2]);
                    if (fieldDetails[2].equalsIgnoreCase("date")) {
                        dateTypeIndex.add(counter);
                    }
                }
            } else {
                logger.error("Issue with metadata file format");
                throw new Exception("Issue with metadata file format");
            }
        } catch (Exception e) {
            logger.error("Exception occurred while initializing fields " + e.getMessage());
            throw e;
        }
    }
}
