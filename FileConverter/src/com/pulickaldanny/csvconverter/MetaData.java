package com.pulickaldanny.csvconverter;

/*This class is a bean class for holding meta data information
 * Properties: columnName, columnLength, columnType
 * Constructors: default & with fields
 * Methods: getter, setters, toString (Override method)*/
public class MetaData {
	private String columnName;
	private int columnLength;
	private String columnType;

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public int getColumnLength() {
		return columnLength;
	}

	public void setColumnLength(int columnLength) {
		this.columnLength = columnLength;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public MetaData(String columnName, int columnLength, String columnType) {
		super();
		this.columnName = columnName;
		this.columnLength = columnLength;
		this.columnType = columnType;
	}

	public MetaData() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "MetaData [columnName=" + columnName + ", columnLength=" + columnLength + ", columnType=" + columnType
				+ "]";
	}

}
