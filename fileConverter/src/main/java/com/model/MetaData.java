package com.model;

/*
 * Metadata entity class.
 */
public class MetaData {

	private String name;

	private int length;

	private EnumType type;

	public MetaData() {

	}

	public MetaData(String name, int length, String type) {
		this.name = name;
		this.length = length;
		setType(type);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public EnumType getType() {
		return type;
	}

	public void setType(String type) {
		if (type.equalsIgnoreCase("string")) {
			this.type = EnumType.STRING;
		} else if (type.equalsIgnoreCase("date")) {
			this.type = EnumType.DATE;
		} else if (type.equalsIgnoreCase("numeric")) {
			this.type = EnumType.NUMERIC;
		}
	}

}
