package fffc;

import java.util.ArrayList;
import java.io.*;
import com.opencsv.*;

/**
 * Java tool to covert fixed files to CSV files.  Can be run as "jar" file or converted to a command line tool
 * using various tools that will facilitate this for Mac, Linux and Windows.
 * @author Alan Bron
 */
public class FixedFileFormatConverter {

	/**
	 * As per the project specification each column of data in the fixed data file can have three data types.
	 */
	private enum ColumnDataType {
		DATE, STRING, NUMERIC
	}

	/**
	 * An inner class created for convenience.  Each instance represents one row of meta data from the meta data file.
	 */
	private class MetaDataRow {
		private final String columnName;
		private final int columnCharLength;
		private final ColumnDataType columnDataType;

		public MetaDataRow(final String columnName, final int columnCharLength, final ColumnDataType columnDataType) {
			this.columnName = columnName;
			this.columnCharLength = columnCharLength;
			this.columnDataType = columnDataType;
		}

		public String getColumnName() {
			return columnName;
		}

		public int getColumnCharLength() {
			return columnCharLength;
		}

		public ColumnDataType getColumnDataType() {
			return columnDataType;
		}
	}

	/**
	 * Fetch the fixed file meta data from the specified metafile
	 * 
	 * @param metaFileName The name of the meta file
	 * @return The meta data as an ArrayList of MetaDataRow
	 * @throws IOException
	 */
	private ArrayList<MetaDataRow> getMetaData(String metaFileName) throws IOException {

		ArrayList<MetaDataRow> metaDataRows = new ArrayList<MetaDataRow>();
		CSVReader reader = null;

		try {
			reader = new CSVReader(new FileReader(metaFileName));
			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {

				ColumnDataType dataType = ColumnDataType.DATE;
				if (nextLine[2].equalsIgnoreCase("string")) {
					dataType = ColumnDataType.STRING;
				} else if (nextLine[2].equalsIgnoreCase("numeric")) {
					dataType = ColumnDataType.NUMERIC;
				}

				int columnCharLength = Integer.decode(nextLine[1]);

				MetaDataRow row = new MetaDataRow(nextLine[0], columnCharLength, dataType);

				metaDataRows.add(row);
			}
		} finally {
			if (reader != null) {
				reader.close();
			}
		}

		return metaDataRows;
	}
	
	/**
	 * Convert String with date in yyyy-mm-dd format to dd/mm/yyyy
	 * 
	 * @param columnString
	 * @return String with data in dd/mm/yyyy format
	 */
	private String convertDateFormat(String columnString) {
		
		String year = columnString.substring(0, 4);
		String month = columnString.substring(5, 7);
		String day = columnString.substring(8, 10);
		
		String reformattedDate = day + "/" + month + "/" + year;
		
		return reformattedDate;
	}

	/**
	 * Convert a file with fixed format data to CSV.  As each bnew row of CSV data is created it is immediately stored into a file because
	 * fixed files can be very large (several GB).
	 * 
	 * @param metaFileName Meta file name
	 * @param fixedFileName Fixed file name
	 * @param csvFileName Output (CSV) file name
	 * @throws IOException If files not found, file incorrectly formatted etc.
	 */
	private void convert(String metaFileName, String fixedFileName, String csvFileName) throws IOException {

		// Get metaData
		ArrayList<MetaDataRow> metaDataRows = getMetaData(metaFileName);

		BufferedReader reader = null;
		CSVWriter writer = null;

		try {
			reader = new BufferedReader(new FileReader(fixedFileName));
			writer = new CSVWriter(new FileWriter(csvFileName), ',');
			String line = null;
			
			// Put column names in CSV filename
			String[] columnNames = new String[metaDataRows.size()];
			for (int i = 0; i < metaDataRows.size(); i++) {
				columnNames[i] = metaDataRows.get(i).getColumnName();
			}
			writer.writeNext(columnNames);
			
			while ((line = reader.readLine()) != null) {

				String[] csvRow = new String[metaDataRows.size()];				
				char[] allChars = line.toCharArray();
				int startIndex = 0;
				for (int i = 0; i < metaDataRows.size(); i++) {
					
					MetaDataRow row = metaDataRows.get(i);
					char[] columnChars = new char[row.getColumnCharLength()];

					for (int j = 0, k = startIndex; j < columnChars.length && k < allChars.length; j++, k++) {				    
						columnChars[j] = allChars[k];					        
					}
					startIndex += columnChars.length;
					
					// Convert column characters to string
					String columnString = new String(columnChars).trim();
					
					// Convert String to CSV column data
					if (row.getColumnDataType() == ColumnDataType.DATE) {
						csvRow[i] = convertDateFormat(columnString);						
					} else {
						csvRow[i] = columnString;
					}
				}
				
				writer.writeNext(csvRow);
			}
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (writer != null) {
				writer.close();
			}
		}
	}

	/**
	 * Java tool to covert fixed files to CSV files.  Can be run as "jar" file or converted to a command line tool
	 * using various tools that will facilitate this for Mac, Linux and Windows.
	 * 
	 * @param args The args required are meta file name, fixed file name and output file name.  Full path names must be specified.
	 */
	public static void main(String[] args) {

		try {

			new FixedFileFormatConverter().convert(args[0], args[1], args[2]);

		} catch (Throwable t) {

			String msg = t.getMessage() == null ? "" : " " + t.getMessage();
			System.out.println(t.getMessage());
		}

	}
}