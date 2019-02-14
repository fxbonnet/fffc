package service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import exception.ErrorLevel;
import exception.FileConvertorException;
import model.CsvMetaData;

/**
 * This class is responsible to read the metadata of the file having fixed file format.
 *   
 * @author jajalvm
 *
 */
@Service
public class CsvMetaDataReader {

	/**
	 * Read the metadata csv file and return the list of CsvMetaData POJOs
	 * 
	 * @param path Metadata CSV file path 
	 * @return list of CsvMetaData POJOs
	 * @throws FileConvertorException when metadata is not as per expectation
	 * @throws IOException 
	 */
	public List<CsvMetaData> readMetaDataFile(final String path) throws FileConvertorException, IOException {
		
		FileInputStream inputStream = null;
		Scanner sc = null;
		ArrayList<CsvMetaData> metadataList = new ArrayList<>();
		try {

			inputStream = new FileInputStream(path);
			sc = new Scanner(inputStream, "UTF-8");
			
		    while (sc.hasNextLine()) {
		        String line = sc.nextLine();
		        String[] metadataArr = line.split(",");
		        if (metadataArr.length < 3) {
		        	throw new FileConvertorException(ErrorLevel.ERROR, "Invalid CSV Meta file entry - [" + line + "]");
		        }
		        CsvMetaData csvMetaData = new CsvMetaData(metadataArr[0], metadataArr[1], metadataArr[2]);
		        metadataList.add(csvMetaData);
		    }
		    
		    return metadataList;
		    
		} catch (FileNotFoundException e) {
			throw new FileConvertorException(ErrorLevel.ERROR, "CSV Metadata file not found");
			
		} finally {
		    if (inputStream != null) {
		        inputStream.close();
		    }
		    if (sc != null) {
		        sc.close();
		    }			
		}
		
	}
}
