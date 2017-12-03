package com.project.utility;

import java.io.*;
import java.util.ArrayList;

import com.project.utility.exception.FileFormatterException;

/**
 * Created by rssridh on 12/3/17.
 */
public class MetaDataReader {

    private String metaDataFileName = "";


    public MetaDataReader(String fileName) {
        this.metaDataFileName = fileName;
    }

    /**
     * TODO
     * @return FileStructure
     */

    public ArrayList<FileStructure> getFileStructure() throws FileFormatterException {
        String readLine;
        String[] columnsData;
        BufferedReader metaDatabufferedReader;
        ArrayList<FileStructure> fileStructureList = new ArrayList<>();

        try {
            File metaDataFile = new File(metaDataFileName);
            metaDatabufferedReader = new BufferedReader(new FileReader(metaDataFile));
        } catch (FileNotFoundException ex) {
            throw new FileFormatterException("Unable to find the metadata file - " + metaDataFileName
                    + ". Please check if the file is in the path provided -" + ex.getMessage());
        }
        System.out.println("Reading the metadata file..");

        try {
            while ((readLine = metaDatabufferedReader.readLine()) != null) {
                FileStructure data = new FileStructure();
                columnsData = readLine.split(",");
                data.setName(columnsData[0]);
                data.setLength(Integer.parseInt(columnsData[1]));
                data.setType(columnsData[2]);
                fileStructureList.add(data);
            }
        } catch (IOException ex) {
            throw new FileFormatterException("Unable to read the metadata file in "
                    + metaDataFileName + " - " + ex.getMessage());
        }

        validateFileStructure(fileStructureList);
        return fileStructureList;
    }

    private void validateFileStructure(ArrayList<FileStructure> fileStructure) throws FileFormatterException {
        if (fileStructure == null || fileStructure.size() == 0) {
            throw new FileFormatterException("Unable to determine the structure of the input file."
                    + "Please valid the metadata file is correct.");
        }
    }

    public String getTitleRow(ArrayList<FileStructure> fileStructure) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;

        for (FileStructure structure : fileStructure) {
            if (!first) {
                sb.append(",");
            }
            sb.append(structure.getName());
            first = false;
        }

        return sb.toString();
    }
}
