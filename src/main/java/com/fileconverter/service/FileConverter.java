package com.fileconverter.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.fileconverter.dto.SourceFileItemStructure;
import com.fileconverter.util.BLException;

public class FileConverter {

	private static final String MESSAGE_ERROR_READING_THE_FILE_STRUCTURE = "Error reading the file with input structure";
	private static final String MESSAGE_FILE_WITH_INPUT_STRUCTURE_DOES_NOT_EXISTS = "The file with input structure does not exists or not available for access";
	private static final String MESSAGE_ERROR_FILE_RW = "Error reading/writing to the file.";
	private static final String MESSAGE_AVAILABLE_FOR_ACCESS = "The file does not exists or not available for access. ";

	public static void main(String[] args){
		FileConverter service = new FileConverter();
		if(args.length != 3) {
			System.out.println("This converter expects 3 parameters: \n"
					+ "1) inputFile path \n"
					+ "2) outputFile path \n"
					+ "3) inputFileStructure path \n"
					+ "i.e c:\\input.txt c:\\output.csv c:\\structure.txt");
			System.exit(0);
		}
		try {
			service.convert(args[0], args[1], args[2], true);
		} catch (BLException e) {
			log(e.getMessage());
		}
	}
	
	
	private List<SourceFileItemStructure> readHelperFile(String fileName) throws BLException {
		try {
			Reader in = new FileReader(fileName);
			try (BufferedReader br = new BufferedReader(in)) {
				LineConverter converter = new LineConverter();
				List<SourceFileItemStructure> list = new ArrayList<>();
				String line = null;
				while ((line = br.readLine()) != null) {
					list.add(converter.parseInputStructure(line));
				}
				return list;
			} catch (IOException e) {
				throw new BLException(MESSAGE_ERROR_READING_THE_FILE_STRUCTURE);
			}
		} catch (FileNotFoundException e) {
			throw new BLException(MESSAGE_FILE_WITH_INPUT_STRUCTURE_DOES_NOT_EXISTS);
		}
	}
	
	/**
	 * The main method of the project - accepts 3 file names and converts fixed lenght input into csv
	 * @param inputFileName
	 * @param outputFileName
	 * @param structureFileName
	 * @param stopFileProcessingOnException - if required, incorect source line may be skipped. Otherwise the processing will be stopped
	 * @throws BLException
	 */
	public void convert(String inputFileName, String outputFileName, String structureFileName, boolean stopFileProcessingOnException) throws BLException {
		try {
			Reader in = new FileReader(inputFileName);
			Writer out = new FileWriter(outputFileName);
			List<SourceFileItemStructure> structure = readHelperFile(structureFileName);
			try (BufferedReader br = new BufferedReader(in); 
				BufferedWriter bw = new BufferedWriter(out)) {
				LineConverter converter = new LineConverter();
				String line = null;
				bw.write(converter.parseHeader(structure));
				while ((line = br.readLine()) != null) {
					try {
						bw.write(converter.parseSourceLine(line, structure));
					} catch (Exception e) {
						log(e.getMessage());
						if(stopFileProcessingOnException) {
							break;
						}
					}
				}
			} catch (IOException e) {
				throw new BLException(e);
			}
		} catch (FileNotFoundException e) {
			throw new BLException(MESSAGE_AVAILABLE_FOR_ACCESS +e.getMessage());
		} catch (IOException e1) {
			throw new BLException(MESSAGE_ERROR_FILE_RW+ e1.getMessage());
		}
		
	}

	private static void log(String message) {
		//TODO Replace with the proper logging if required
		System.out.println(message);
	}
}
