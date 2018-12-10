package com.octo.interview;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Logger;

import com.octo.interview.exceptions.FileFormatException;
import com.octo.interview.metadata.Metadata;
import com.octo.interview.parsers.FixedWidthFile;
import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;



public class FileParser
{
	private final static Logger LOGGER = Logger.getLogger(FileParser.class.getName());
	private final static String CRLF = "\r\n";

	public static void main(String[] args) {
		
		try {
			
			if(args == null || args.length < 3) {
				
				printUsage();
				LOGGER.severe("Invalid number of arguments");
				
				return;
			}
			
			Metadata metadata = Metadata.fromCsvFile(args[0]);
			FixedWidthFile file = FixedWidthFile.fromMetadata(metadata, args[1]);
			file.open();
			// write to CSV
			CsvWriterSettings csvSettings = new CsvWriterSettings();
			
			csvSettings.getFormat().setLineSeparator(CRLF);
			
			CsvWriter writer = new CsvWriter(new File(args[2]), csvSettings);
			writer.writeHeaders(file.getColNames());
			List<String[]> rows;
			while ((rows = file.readRowsOfSize(64)) != null) {
				writer.writeStringRows(rows);	
				LOGGER.info( String.format("Processing %d MB", 64));
			}
			
			writer.close();
			LOGGER.info("Output csv file complete");

		} catch (UnsupportedEncodingException | FileNotFoundException e1) {
			LOGGER.severe(e1.getMessage());
			LOGGER.severe(e1.getStackTrace().toString());
		}
		catch (ParseException e) {
			LOGGER.severe(e.getMessage());
			LOGGER.severe(e.getStackTrace().toString());
		} catch (FileFormatException e) {
			LOGGER.severe(e.getMessage());
		}
		
		

	}

	private static void printUsage() {
		System.out.println("usage: The application require three arguments to the application: <metadata_file_path> <fixed_width_faile_path> <output_csv_file_path>");
		
	}
	

	


	

}
