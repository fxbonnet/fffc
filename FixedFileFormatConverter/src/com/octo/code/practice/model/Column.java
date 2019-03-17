package com.octo.code.practice.model;

import com.octo.code.practice.enums.ColumnTypeEnum;

public class Column {
	private String name;
	private int rowStartIndex;
	private int rowEndIndex;
	private ColumnTypeEnum type;
	
	public Column(String name, int rowStartIndex, int rowEndIndex, String type) {
		this.name = name;
		this.rowStartIndex = rowStartIndex;
		this.rowEndIndex = rowEndIndex;
		this.setType(type);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRowEndIndex() {
		return rowEndIndex;
	}

	public void setRowEndIndex(int rowEndIndex) {
		this.rowEndIndex = rowEndIndex;
	}

	public int getRowStartIndex() {
		return rowStartIndex;
	}

	public void setRowStartIndex(int rowStartIndex) {
		this.rowStartIndex = rowStartIndex;
	}

	public ColumnTypeEnum getType() {
		return type;
	}

	public void setType(String type) {
		if (type != null) {
			if (type.toUpperCase().trim().equals("DATE")) {
				this.type = ColumnTypeEnum.DATE;
			} else if (type.toUpperCase().trim().equals("NUMERIC")) {
				this.type = ColumnTypeEnum.NUMBERIC;
			} else {
				this.type = ColumnTypeEnum.STRING;
			}
		} else {
			this.type = ColumnTypeEnum.STRING;
		}
	}
}
