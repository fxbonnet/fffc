package com.octotechnology.fffc;

import java.util.Objects;

import com.octotechnology.fffc.converter.FixedFileFormatConverter;

/**
 * The fixed file format converter main class
 * 
 * @author roshith
 *
 */
public class FileFormatConverterApp {

	private static String inputFilePath;
	private static String metaDataFilePath;
	private static String outPutFilePath;

	public static void main(String[] args) {
		FixedFileFormatConverter fixedFileFormatConverter = new FixedFileFormatConverter();
		if (validateArgs(args)) {
			ConfigData configData = new ConfigData(inputFilePath, metaDataFilePath, outPutFilePath);
			fixedFileFormatConverter.run(configData);
		}
	}

	public static boolean validateArgs(String[] args) {
		if (!Objects.isNull(args) && args.length == 3) {
			inputFilePath = args[0];
			metaDataFilePath = args[1];
			outPutFilePath = args[2];
		} else {
			System.out.println("Usage: \n\t java -jar fffc.jar inputfilePath metadatafilePath outputFilePath \n");
			System.out.println(
					"Eg: java -jar fffc.jar C:\\MyInputFile.txt C:\\MyMetadataFile.txt C:\\MyOutPutFile.csv \n");
			return false;
		}
		return true;
	}
}
