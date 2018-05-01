package com.mrdaydreamer.model;

public class MetaInfo {
	private String columnName;
	private int columnWidth;
	private String dataType;
	
	public MetaInfo(String columnName, int columnWidth, String dataType) {
		this.columnName = columnName;
		this.columnWidth = columnWidth;
		this.dataType = dataType;
	}
	
	public String getColumnName() {
		return columnName;
	}

	public int getColumnWidth() {
		return columnWidth;
	}

	public String getDataType() {
		return dataType;
	}
}
