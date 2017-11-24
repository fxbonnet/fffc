package com.octo.jramilo.fffc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.octo.jramilo.fffc.exception.InvalidFileExpection;
import com.octo.jramilo.fffc.exception.InvalidFormatException;
import com.octo.jramilo.fffc.model.Metadata;
import com.octo.jramilo.fffc.model.MetadataColumn;
import com.octo.jramilo.fffc.model.MetadataType;
import com.octo.jramilo.fffc.util.FileValidator;

/**
 * A helper class for metadata operations.
 * 
 * @author jacobramilo
 */
public enum MetadataHelper {
	INSTANCE;
	
	private static final int MAX_METADATA_COLS = 3;
	
	/**
	 * A method that describes the metadata based on a given file. 
	 * This method is used in conjunction with {@link FixedFileFormatConverter}
	 * to convert a given data file following the metadata description.
	 * 
	 * @param metadataFile - This is the file where the metadata is described.
	 * Each line has column name, length and type.
	 * 
	 * @return {@link MetadataDescriptor} - the described metadata object
	 * @throws IOException - thrown when there is an Input/Output exception
	 * @throws InvalidFileExpection - thrown if the file supplied in invalid
	 * @throws InvalidFormatException - thrown when the file contents are invalid
	 */
	public MetadataDescriptor describe(final File metadataFile) 
			throws IOException, InvalidFileExpection, InvalidFormatException {
		FileValidator.validate(metadataFile, true);
		
		MetadataDescriptor descriptor = new MetadataDescriptor();
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
	
		try {
			fileReader = new FileReader(metadataFile);
			bufferedReader = new BufferedReader(fileReader);
			
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				String[] fields = line.split(",");
				if(fields.length != MAX_METADATA_COLS) {
					throw new InvalidFormatException("Invalid metadata file!");
				}
				String name = fields[MetadataColumn.NAME.getIndex()];
				int length = Integer.parseInt(fields[MetadataColumn.LENGTH.getIndex()]);
				MetadataType type = MetadataType.getMetaType(fields[MetadataColumn.TYPE.getIndex()]);
				
				Metadata metadata = new Metadata(name, length, type);
				descriptor.add(metadata);
			}
		} finally {
			try {
				if(bufferedReader != null) {
					bufferedReader.close();
				}
				
				if(fileReader != null) {
					fileReader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return descriptor;
	}

}
