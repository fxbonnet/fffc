package com.fileConvert;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import com.common.CustomMessage;
import com.exception.CustomException;
import com.model.MetaData;

/*
 * This class reads the Metadata file line by line.
 */
public class MetaDataFileReader {

	public MetaDataFileReader() {

	}

	/**
	 * This method reads metadata file line by line.
	 * 
	 * @param metaDataBufferReader A variable of type BufferedReader.
	 * @return metadataValues A variable of type List<MetaData>.
	 * @exception CustomException On error.
	 */

	public List<MetaData> parseMetaFile(BufferedReader metaDataBufferReader) throws CustomException {
		try {
			List<MetaData> metadataValues = new ArrayList<MetaData>();
			MetaData metadata;
			String line = "";

			while ((line = metaDataBufferReader.readLine()) != null) {

				String[] fields = line.split(CustomMessage.METADATA_DELIMITER);

				if (fields.length == 3) {
					metadata = new MetaData(fields[0], Integer.parseInt(fields[1]), fields[2]);
					metadataValues.add(metadata);
				}
			}
			return metadataValues;
		} catch (Exception e) {
			throw new CustomException(CustomMessage.METADATA_PARSING);
		}

	}

	/**
	 * This method add metadata names in header of CSV outfile file.
	 * 
	 * @param metadataValues A variable of type List<MetaData>.
	 * @return headerNames A variable of type String.
	 * @exception CustomException On error.
	 */
	public String writeCSVHeader(List<MetaData> metadataValues) {
		StringBuffer headerNames = new StringBuffer();
		for (int i = 0; i < metadataValues.size(); i++) {
			headerNames.append(metadataValues.get(i).getName());
			if (i < metadataValues.size() - 1)
				headerNames.append(CustomMessage.COMMA);
		}
		return headerNames.append(CustomMessage.NEW_LINE).toString();
	}
}
