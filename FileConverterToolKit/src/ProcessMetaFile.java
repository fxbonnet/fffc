import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;


public class ProcessMetaFile {
	private final List<String> ACCDATATYPES = Arrays.asList("date", "numeric", "string");
	private static final String COLUMN_DELIMITER = ",";
	private static final String ENCODING = "UTF-8";
    
	private MetaDataDO metaDataDO;
    
	private Map<String,Integer> columnDataLengthMap = new LinkedHashMap<String,Integer>();
	private Map<String,String> columnDataTypeMap = new LinkedHashMap<String,String>();
	private String metaFileLocation;
	private int totalLineLength = 0;
	
	
	public ProcessMetaFile (String metaFileLocation) {
		this.metaFileLocation = metaFileLocation;
	}
	
	public MetaDataDO processMetaFileData() {
		BufferedReader br = null;
		try 
		{ 
			br = new BufferedReader(new BufferedReader(new InputStreamReader(
                    new FileInputStream(metaFileLocation), ENCODING)));
			String currLine = "";
	        while ((currLine = br.readLine()) != null) {

	            // use comma as separator
	            String[] currMeta = currLine.split(COLUMN_DELIMITER);

				validateMetaDataLine(currMeta);
				
				// update the MetaDataDO object
				metaDataDO =new MetaDataDO();
				metaDataDO.setColumnDataLengthMap(columnDataLengthMap);
				metaDataDO.setColumnDataTypeMap(columnDataTypeMap);
				metaDataDO.setTotalLengthPerLine(totalLineLength);

	        }

	    } catch (IOException e) {
	    	throw new RuntimeException ("Error during accessing/reading the meta data file in path:"+metaFileLocation);
		} finally {
			try {
				br.close();
            } catch (IOException e) {
            	throw new RuntimeException ("Error while closing Buffered Reader that was reading the Meta Data File.");
            }
		}
		
		return metaDataDO;
	}
	
	/**Method to validate the meta data file. The meta data file is expected to:
	1. have 3 details that are comma (,) separated. The column name, Data Length and Data Type
	2. The data types can be date, numeric and string 
	3. The meta data cannot have duplicate column names.
	 * @throws ServletException 
	*/
	
	private void validateMetaDataLine(String[] currMeta) {
		
		String columnName;
		int dataLength;
		String dataType;
		
		// Error out if the number of details in the given line is more than 3.
		if (currMeta == null || currMeta.length != 3) {
			throw new RuntimeException ( "Meta data file cannot contain more than 3 comma separated details. Accepted Format-column name, Data Length, Data Type." ) ;
		}
		
		if (currMeta[0] == null || currMeta[0].length() == 0) {
			throw new RuntimeException ( "Meta data file cannot be processed. Column name cannot be blank.");
		}	
		
		
		// Validate the data length
		validateDataLengthAndType(currMeta[1],currMeta[2]);
		
		// assign values
		columnName = currMeta[0].trim();
		dataLength = Integer.parseInt(currMeta[1]);
		dataType = currMeta[2].trim().toLowerCase();
		
		// A column name can only occur once.
		if (columnDataLengthMap.containsKey(columnName) || columnDataTypeMap.containsKey(columnName)) {
			throw new RuntimeException ( "Meta data file cannot be processed. \nColumn name "+columnName+" is repeated. A column name can only occur once.");
		}
		
		totalLineLength = totalLineLength+dataLength;
		columnDataLengthMap.put(columnName,dataLength);
		columnDataTypeMap.put(columnName,dataType);		
	}
	
	/**Method to validate the data Type.	
	*/
	
	private void validateDataLengthAndType(String dataLength,String dataType) {
		
		// Error out if the number of details in the given line is more than 3.
		if (dataType == null || dataType.trim().length() ==0 || (!ACCDATATYPES.contains(dataType.trim().toLowerCase()))) {
			throw new RuntimeException ( "Meta data file cannot be processed. Data Type "+dataType+" is not supported.");
		}
		
		// Data Length must be a numeric value.
		 try
        {
            Integer.parseInt(dataLength);
        } 
        catch (NumberFormatException e) 
        {
            throw new RuntimeException ( "Meta data file cannot be processed. Data Length must be numeric. Value "+dataLength+" is not supported.");

        }
		
		// if dataType is Date, the defined data length must be 10
		if (dataType.trim().equalsIgnoreCase("date")) {
			if (Integer.parseInt(dataLength) != 10) {
				throw new RuntimeException ( "Meta data file cannot be processed. Data Length must be 10 for the data type of a 'Date'. Value "+dataLength+" is not supported.");
			
			}
		}
		
	}
}
