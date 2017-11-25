package com.octo.jramilo.fffc.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import com.octo.jramilo.fffc.FixedFileFormatConverter;
import com.octo.jramilo.fffc.MetadataDescriptor;
import com.octo.jramilo.fffc.exception.InvalidFormatException;
import com.octo.jramilo.fffc.model.Metadata;
import com.octo.jramilo.fffc.model.MetadataColumn;
import com.octo.jramilo.fffc.model.MetadataType;

/**
 * A class for parsing the metadata.
 * 
 * @author jacobramilo
 */
public enum MetadataParser {
	INSTANCE;
	
	/**
	 * A method that parses the metadata based on a given file. 
	 * This method is used in conjunction with {@link FixedFileFormatConverter}
	 * to convert a given data file following the metadata description.
	 * 
	 * @param metadataFile - This is the file where the metadata is described.
	 * Each line has column name, length and type.
	 * 
	 * @return {@link MetadataDescriptor} - the described metadata object
	 * @throws IOException - thrown when there is an Input/Output exception
	 * @throws InvalidFormatException - thrown when the file contents are invalid
	 */
	public MetadataDescriptor parse(final File metadataFile) 
			throws IOException, InvalidFormatException {
		FileValidator.validate(metadataFile, true);
		
		MetadataDescriptor descriptor = null;
		LineIterator it = FileUtils.lineIterator(metadataFile, Constant.CHARSET_UTF8);
		try {
			descriptor = new MetadataDescriptor();
		    while (it.hasNext()) {
		        String line = it.nextLine();
		        String[] fields = line.split(Constant.COMMA);
				if(fields.length != Constant.MAX_METADATA_COLS) {
					throw new InvalidFormatException("Invalid metadata file!");
				}
				String name = fields[MetadataColumn.NAME.getIndex()];
				int length = Integer.parseInt(fields[MetadataColumn.LENGTH.getIndex()]);
				MetadataType type = MetadataType.getMetaType(fields[MetadataColumn.TYPE.getIndex()]);
				
				Metadata metadata = new Metadata(name, length, type);
				descriptor.add(metadata);
		    }
		} finally {
		    LineIterator.closeQuietly(it);
		}
		
		return descriptor;
	}

}
