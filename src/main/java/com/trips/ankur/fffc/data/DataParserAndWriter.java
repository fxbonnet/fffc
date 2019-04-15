package com.trips.ankur.fffc.data;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.trips.ankur.fffc.columns.Column;
import com.trips.ankur.fffc.columns.ColumnType;
import com.trips.ankur.fffc.exceptions.InvalidFieldFormatException;
import com.trips.ankur.fffc.exceptions.InvalidLineFormatException;
import com.trips.ankur.fffc.metadata.MetaData;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.substring;

/**
 * This class performs the Parsing of data and it writes the data in the output
 * file.
 * 
 * @author tripaank
 *
 */

@Getter
@Setter
public class DataParserAndWriter {
	Logger logger = LoggerFactory.getLogger(DataParserAndWriter.class);

	private File metadataFile;
	private File dataFile;
	private final DateTimeFormatter informat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private final DateTimeFormatter outformat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	// private SimpleDateFormat sdf = new SimpleDateFormat();

	public DataParserAndWriter(File metadataFile, File dataFile) {
		this.metadataFile = metadataFile;
		this.dataFile = dataFile;
	}

	/**
	 * This is to pars the metadata.
	 * 
	 * @return
	 * @throws IOException
	 */

	public MetaData parseMetaData() throws IOException {
		logger.info("[parseMetaData] Parsing the metadata file.");
		MetaData metaData = new MetaData();
		Reader r = new FileReader(metadataFile);
		Object[] records = IteratorUtils.toArray(CSVFormat.RFC4180.parse(IOUtils.toBufferedReader(r)).iterator());
		Column[] columns = new Column[records.length];

		for (int i = 0; i < records.length; i++) {
			CSVRecord record = (CSVRecord) records[i];
			String columnName = record.get(0);
			long columnLength = Long.parseLong(record.get(1));
			ColumnType columnType = getColumnType(record.get(2));
			Column c = new Column(columnName, columnLength, columnType);
			columns[i] = c;
		}
		metaData.setColumns(columns);
		logger.info("[parseMetaData] Parsing the metadata file completes.");
		logger.debug("[parseMetaData] Metadata information is : " + metaData.toString());
		return metaData;
	}

	/**
	 * Return the column type.
	 * 
	 * @param type
	 * @return CloumnType
	 */
	private ColumnType getColumnType(String type) {
		if (StringUtils.isNotBlank(type) && StringUtils.equalsIgnoreCase(ColumnType.DATE.toString(), type))
			return ColumnType.DATE;
		if (StringUtils.isNotBlank(type) && StringUtils.equalsIgnoreCase(ColumnType.NUMERIC.toString(), type))
			return ColumnType.NUMERIC;
		if (StringUtils.isNotBlank(type) && StringUtils.equalsIgnoreCase(ColumnType.STRING.toString(), type))
			return ColumnType.STRING;
		return ColumnType.UNIDENTIFIED;
	}

	/**
	 * Perform parsing and writing the output data. This method reads the data line
	 * by line, pars the data and writes it in the output file.
	 * 
	 * @param metaData
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public boolean parseDataAndWriteOutput(MetaData metaData, String fileName, boolean writeError) throws Exception {

		logger.info("[parseDataAndWriteOutput] Start Parsing the data file for writing.");

		try (LineIterator it = FileUtils.lineIterator(dataFile, StandardCharsets.UTF_8.name())) {
			String header = Arrays.stream(metaData.getColumns()).map(Column::getColumnName)
					.collect(Collectors.joining(","));
			File output = new File(fileName);

			// In case we want the program to continue even when a line is not in correct
			// format.
			// We can add an error file and add the incorrect lines in the that without
			// throwing the exception.
			File errorFile = new File(fileName.concat(".error"));

			FileUtils.write(output, format("%s\r\n", header), StandardCharsets.UTF_8);

			StringBuffer[] data = new StringBuffer[metaData.getColumns().length];

			Column[] columns = metaData.getColumns();
			int totalDefinedLength = metaData.returnTotalLineLength();

			Iterable<String> iterable = () -> (Iterator<String>) it;
			StreamSupport.stream(iterable.spliterator(), true).forEach(line -> {

				// If the line length is not as total length, the format is not correct.
				// Add it to error file instead of throwing exception
				
				if (line.length() != totalDefinedLength) {
					if (writeError) {
						logger.error("[parseDataAndWriteOutput] Current line from the data file is not in correct format, will be added to the Error file.");
						try {
							FileUtils.writeStringToFile(errorFile, line, StandardCharsets.UTF_8, true);
						} catch (Exception e) {
							logger.error("[parseDataAndWriteOutput] Error while adding the error file.");
						}
						logger.debug("[parseDataAndWriteOutput] Going to read the next line.");
						return;

					} else {
						throw new InvalidLineFormatException(
								"File format incorrect: Line length is not as per the metdata - " + line);

					}
					
				}
				int start;
				int end = 0;
				for (int i = 0; i < columns.length; i++) {
					data[i] = null;

					Column column = columns[i];
					start = i == 0 ? 0 : end;
					end = start + Math.toIntExact(column.getColumnLength());

					StringBuffer substring = new StringBuffer();

					if (column.getColumnType() == ColumnType.DATE) {
						try{
							substring.append(LocalDate.parse(substring(line, start, end).trim(), informat).format(outformat));
						}catch (Exception e){
							
							if(writeError) {
								logger.error("[parseDataAndWriteOutput] Incorrect Date Format : Current line from the data file is not in correct format, will be added to the Error file.");
								try {
									FileUtils.writeStringToFile(errorFile, line, StandardCharsets.UTF_8, true);
								} catch (Exception e1) {
									logger.error("[parseDataAndWriteOutput] Error while adding the error file.");
								}
								logger.debug("[parseDataAndWriteOutput] Going to read the next line.");
								return;
								
							} else {
								throw new InvalidFieldFormatException("File format incorrect: Input date format not correct, Date is - "+substring(line, start, end).trim() + ", Expected format: "+informat.toString());
							}
							
						}
								
					} else {
						substring.append(substring(line, start, end).trim());

					}

					if (substring.indexOf(",") > -1) {
						substring.insert(0, "\"").append("\"");
					}

					data[i] = substring;
				}

				String record = Arrays.stream(data).collect(Collectors.joining(","));
				try {
					FileUtils.writeStringToFile(output, format("%s\r\n", record), StandardCharsets.UTF_8, true);
				} catch (IOException e) {
					throw new RuntimeException(e.getMessage(), e);
				}

			});
		}

		logger.info("[parseDataAndWriteOutput] End of Parsing the data file and writing output to output file.");
		return true;
	}
}
