package common;

/**
 * Utility class with helper methods.
 * 
 * @author jajalvm
 *
 */
public class Utils {

	/**
	 * Enum for column type as a part of metadata.
	 * 
	 */
	public enum META_DATA_CSV_COLUMN_TYPE {
		STRING,
		NUMERIC,
		DATE;
		
		public static META_DATA_CSV_COLUMN_TYPE getMetaDataCsvColumnType(String type){
			switch(type.toUpperCase()){
				case "STRING":
					return META_DATA_CSV_COLUMN_TYPE.STRING;
				case "NUMERIC":
					return META_DATA_CSV_COLUMN_TYPE.NUMERIC;
				case "DATE":
					return META_DATA_CSV_COLUMN_TYPE.DATE;
					
				default:
						return null;
			}
		}
		
		public String toString(){
			return this.toString().toLowerCase();
		}
	}	


	/**
	 * Verify the given column type.
	 *   
	 * @param type metadata column type. Must be one of String, Date or Numeric 
	 * @return boolean - true only if the type is one the supported types. 
	 */
	public static boolean checkMetaDataColumnType(final String type) {
		boolean isValid = true;
		META_DATA_CSV_COLUMN_TYPE csvColType = META_DATA_CSV_COLUMN_TYPE.getMetaDataCsvColumnType(type);
		if (csvColType == null) {
			isValid = false;
		}
		return isValid;
	}


}



