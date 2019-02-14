package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.univocity.parsers.common.ParsingContext;
import com.univocity.parsers.common.processor.ObjectRowProcessor;
import com.univocity.parsers.common.processor.ObjectRowWriterProcessor;
import com.univocity.parsers.conversions.Conversions;
import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;
import com.univocity.parsers.fixed.FixedWidthFields;
import com.univocity.parsers.fixed.FixedWidthParser;
import com.univocity.parsers.fixed.FixedWidthParserSettings;

import common.Utils.META_DATA_CSV_COLUMN_TYPE;
import exception.ErrorLevel;
import exception.FileConvertorException;
import model.CsvMetaData;

/**
 * This the class that does the bulk of tasks to convert the fixed format file to CSV file, based the metadata provided.
 *  
 * @author jajalvm
 *
 */
@Service
public class FixedFileFormatProcessor {

	private final static Logger logger = LoggerFactory.getLogger(FixedFileFormatProcessor.class);
	
	// The Fixed File format file
	private String inputFile;
	
	// The output csv file
	private String outputFile;
	
	// Getters and Setters
	public String getInputFile() {
		return inputFile;
	}

	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	public String getOutputFile() {
		return outputFile;
	}

	public void setOutputFile(String outputFile) {
		this.outputFile = outputFile;
	}

	/**
	 * Process the input file and produce the CSV file as per the provided metadata.
	 *  
	 * @param metaData Metadata specifying the structure of data file to be processed 
	 * @throws FileConvertorException Application level exception
	 */
	public void processInputFile(List<CsvMetaData> metaData) throws FileConvertorException {
		
		logger.info("Proccesing the input data file...");
		
		// verify the meta data size 
		if (metaData.size() == 0) {
			// Error as no meta data available
			throw new FileConvertorException(ErrorLevel.ERROR, "CSV Meta data not provided."); 
		}
		
		// CSV Writer Processor
		ObjectRowWriterProcessor writeProcessor = new ObjectRowWriterProcessor();
		
		// CSV Writer Settings
		CsvWriterSettings csvWriterSettings = defineCsvWriterSettings();
		
		csvWriterSettings.setRowWriterProcessor(writeProcessor);
		
		// Define the column transformations for writing the CSV file
		convertFieldsForWrite(writeProcessor, metaData);
		
		// CSV Writer to generate the output file 
		CsvWriter writer = new CsvWriter(getWriter(), csvWriterSettings);

		
		// ObjectRowProcessor converts the parsed values and gives the resulting row.
		ObjectRowProcessor rowProcessor = new ObjectRowProcessor() {
			@Override
			public void rowProcessed(Object[] row, ParsingContext context) {
				// process each row to apply the custom transformations (for e.g. date field) internally
				writer.processRecord(row);
			}
		};

		// Define the column transformations for reading the fixed format data file.
		convertFieldsForRead(rowProcessor, metaData);
		
		// Map of column names and widths, populated using metadata
		LinkedHashMap<String, Integer> mapOfColumnsAndLengths = getColumnNamesAndLengths(metaData);
		
		// Write the column headers 
		writer.writeHeaders(mapOfColumnsAndLengths.keySet());
		
		// Fixed width column settings
		FixedWidthFields fixedWidthFields = new FixedWidthFields(mapOfColumnsAndLengths);
		FixedWidthParserSettings parserSettings = new FixedWidthParserSettings(fixedWidthFields);
		
		// line seperator CRLF
		parserSettings.getFormat().setLineSeparator("\r\n");
		parserSettings.setProcessor(rowProcessor);
		
		// parser for the datafile (input file)
		FixedWidthParser parser = new FixedWidthParser(parserSettings);
		
		parser.beginParsing(getReader());
		
		String[] row;
		while ((row = parser.parseNext()) != null) {
			// do nothing here ...
			// The processing of the every row is performed by rowProcessor.
		}
		
		parser.stopParsing();
		writer.close();
		
		logger.info("Proccesing of the input data file completed.");
	}
	
	/**
	 * Get the column names and lengths from the metadata that define the structure of data file.
	 * 
	 * @param metaData defines the structure of data file
	 * @return LinkedHashMap containing the column name and length. This map retains the order of metadata entries.
	 */
	private LinkedHashMap<String, Integer> getColumnNamesAndLengths(List<CsvMetaData> metaData) {
		
		LinkedHashMap<String, Integer> fieldsAndLengths = new LinkedHashMap<String, Integer>();
		
		for (CsvMetaData meta : metaData) {
			fieldsAndLengths.put(meta.getColName(), Integer.valueOf(meta.getColLength()));
		}
		
		return fieldsAndLengths;
	}
	
	/**
	 * Apply  the column transformations for reading the fixed format data file.
	 * 
	 * @param rowProcessor ObjectRowProcessor
	 * @param metaData List containing the CsvMetaData POJOs
	 */
	private void convertFieldsForRead(ObjectRowProcessor rowProcessor, List<CsvMetaData> metaData) {
		for (CsvMetaData meta : metaData) {
			// Set the date conversions
			if (META_DATA_CSV_COLUMN_TYPE.getMetaDataCsvColumnType(meta.getColType()) == META_DATA_CSV_COLUMN_TYPE.DATE) {
				rowProcessor.convertFields(Conversions.notBlank(), Conversions.toDate("yyyy-mm-dd")).set(meta.getColName());
			}
			// set string conversions
			if (META_DATA_CSV_COLUMN_TYPE.getMetaDataCsvColumnType(meta.getColType()) == META_DATA_CSV_COLUMN_TYPE.STRING) {
				rowProcessor.convertFields(Conversions.notBlank()).set(meta.getColName());
			}
			// set number format conversions
			if (META_DATA_CSV_COLUMN_TYPE.getMetaDataCsvColumnType(meta.getColType()) == META_DATA_CSV_COLUMN_TYPE.NUMERIC) {
				rowProcessor.convertFields(Conversions.notBlank(), Conversions.formatToNumber("###.####")).set(meta.getColName());
			}
		} 
		
	}
	
	/**
	 * Apply  the column transformations for writing the output CSV file.
	 * 
	 * @param rowProcessor ObjectRowWriterProcessor
	 * @param metaData List containing the CsvMetaData POJOs
	 */
	private void convertFieldsForWrite(ObjectRowWriterProcessor rowProcessor, List<CsvMetaData> metaData) {
		Locale locale = Locale.ENGLISH;
		
		for (CsvMetaData meta : metaData) {
			// Set the date conversions
			if (META_DATA_CSV_COLUMN_TYPE.getMetaDataCsvColumnType(meta.getColType()) == META_DATA_CSV_COLUMN_TYPE.DATE) {
				// rowProcessor.convertFields(Conversions.toDate(locale,"dd/mm/yyyy")).add(meta.getColName());
				// Conversions.toDate(locale, 'dd/mm/yyyy').
				// rowProcessor.convertFields(Conversions.toDate(locale,"dd/mm/yyyy")).set(meta.getColName());
				rowProcessor.convertFields(Conversions.toDate(locale,"dd/mm/yyyy")).add(meta.getColName());
			}
			// set string conversions
			if (META_DATA_CSV_COLUMN_TYPE.getMetaDataCsvColumnType(meta.getColType()) == META_DATA_CSV_COLUMN_TYPE.STRING) {
				rowProcessor.convertFields(Conversions.trim()).add(meta.getColName());
			}
			// set number format conversions
			if (META_DATA_CSV_COLUMN_TYPE.getMetaDataCsvColumnType(meta.getColType()) == META_DATA_CSV_COLUMN_TYPE.NUMERIC) {
				rowProcessor.convertFields(Conversions.formatToNumber("###.####")).add(meta.getColName());
			}
		} 
		
	}
	
	
	/**
	 * The Stream to read the data file.
	 * 
	 * @return Reader
	 * @throws FileConvertorException
	 */
	private Reader getReader() throws FileConvertorException {
		try {
			InputStream inputStream = new FileInputStream(inputFile);
			return new InputStreamReader(inputStream, StandardCharsets.UTF_8);
		} catch (FileNotFoundException e) {
			throw new FileConvertorException(ErrorLevel.ERROR, "CSV Input file not found - " + e.getMessage());
		}
	}
	
	/**
	 * The Stream to write the data file.
	 * 
	 * @return Writer
	 * @throws FileConvertorException
	 */
	private Writer getWriter() throws FileConvertorException {
		try {
			File out = new File(outputFile);
			out.createNewFile();
			return new OutputStreamWriter(new FileOutputStream(outputFile), StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new FileConvertorException(ErrorLevel.ERROR, "Error creating CSV output file - " + e.getMessage());
		}
		
	}
	
	/**
	 * Create CsvWritingSettings and apply the settings for escape quotes.
	 * 
	 * @return CsvWriterSettings
	 */
	private CsvWriterSettings defineCsvWriterSettings() {
		
		CsvWriterSettings csvWriterSettings = new CsvWriterSettings();
		csvWriterSettings.getFormat().setQuote('\'');
		csvWriterSettings.getFormat().setQuoteEscape('\'');
		
		return csvWriterSettings;
	}
	
}
