package model;

import org.apache.commons.lang3.StringUtils;

import common.Utils;
import exception.ErrorLevel;
import exception.FileConvertorException;

/**
 * This is a POJO class, used for the CSV meta data containing the information of a fixed file format.
 * 
 * @author jajalvm
 *
 */
public class CsvMetaData {

	private String colName;
	private String colLength;
	private String colType;
	
	/**
	 * Constructor for a CSV Meta data. It performs the validation for name, length and type.
	 * 
	 * @param name    name of the csv column
	 * @param length  length of the csv column
	 * @param type
	 * @throws FileConvertorException when validation fails
	 */
	public CsvMetaData(final String name, final String length, final String type) throws FileConvertorException {
		
		if (StringUtils.isBlank(name)
			|| StringUtils.isBlank(length)
			|| StringUtils.isBlank(type)) {
			throw new FileConvertorException(ErrorLevel.FATAL, "Invalid CSV meta data. Name - ["+ name + "] Length - [" + length + "] Type - [" + type + "]");
		}
		
		if (!Utils.checkMetaDataColumnType(type)) {
			throw new FileConvertorException(ErrorLevel.FATAL, "Invalid CSV meta data column type - [" + type + "]");
		}
		
		this.colName = name;
		this.colLength = length;
		this.colType = type;
	}
	
	// Getters and Setters
	public String getColName() {
		return colName;
	}
	public void setColName(String colName) {
		this.colName = colName;
	}
	public String getColLength() {
		return colLength;
	}
	public void setColLength(String colLength) {
		this.colLength = colLength;
	}
	public String getColType() {
		return colType;
	}
	public void setColType(String colType) {
		this.colType = colType;
	}

	@Override
	public String toString() {
		return "CsvMetaData [colName=" + colName + ", colLength=" + colLength + ", colType=" + colType + "]";
	}

	
	
}
