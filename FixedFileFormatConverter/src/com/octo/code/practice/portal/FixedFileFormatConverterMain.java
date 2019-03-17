package com.octo.code.practice.portal;

import java.util.List;

import com.octo.code.practice.exception.CSVConverterCustomizedException;
import com.octo.code.practice.file.FixedFileCSVConverter;
import com.octo.code.practice.file.MetadataFileReader;
import com.octo.code.practice.model.Column;

public class FixedFileFormatConverterMain {

	public static void main(String[] args) {
		String dataFilePath = "src\\com\\octo\\code\\practice\\resource\\data.txt";
		String metadataFilePath = "src\\com\\octo\\code\\practice\\resource\\metadata.txt";
		String generatedCSVFilePath = "src\\com\\octo\\code\\practice\\resource\\output";
		
		MetadataFileReader matadataFileReader = new MetadataFileReader();
		FixedFileCSVConverter fixedFileReader = new FixedFileCSVConverter();
		try {
			List<Column> columns = matadataFileReader.loadMetadata(metadataFilePath);
			fixedFileReader.generateCSVFiledHeader(generatedCSVFilePath, columns);
			fixedFileReader.generateCSVFileBody(dataFilePath, generatedCSVFilePath, columns);
		} catch(CSVConverterCustomizedException e) {
			System.out.println(e.getMessage());
		}
	}

}
