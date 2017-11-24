package com.octo.jramilo.fffc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.octo.jramilo.fffc.exception.InvalidMetadataFileException;
import com.octo.jramilo.fffc.model.Metadata;
import com.octo.jramilo.fffc.model.MetadataColumn;

public class MetadataDescriptor {
	private ArrayList<Metadata> metadataList;
	
	public MetadataDescriptor() {
		metadataList = new ArrayList<Metadata>();
	}
	
	public void describe(File metadataFile) throws InvalidMetadataFileException, IOException {
		if(metadataFile == null) {
			throw new IllegalArgumentException("The metadata file cannot be null!");
		}
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
	
		try {
			fileReader = new FileReader(metadataFile);
			bufferedReader = new BufferedReader(fileReader);
			
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				String[] fields = line.split(",");
				validateNumberOfFields(fields);
				String name = fields[MetadataColumn.NAME.getIndex()];
				int length = Integer.parseInt(fields[MetadataColumn.LENGTH.getIndex()]);
				String type = fields[MetadataColumn.TYPE.getIndex()];
				
				Metadata metadata = new Metadata(name, length, type);
				metadataList.add(metadata);
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
	}
	
	public List<Metadata> getMetadataList() {
		return metadataList;
	}
	
	private void validateNumberOfFields(String[] fields) throws InvalidMetadataFileException {
		if(fields.length != 3) {
			throw new InvalidMetadataFileException();
		}
	}
}
