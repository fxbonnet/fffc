package com.fileconverter.dto;

import com.fileconverter.dto.types.ItemType;

public class SourceFileItemStructure {
	private String name;
	private int length;
	private ItemType type;

	public SourceFileItemStructure(String name, int length, ItemType type) {
		super();
		this.name = name;
		this.length = length;
		this.type = type;
	}
	public ItemType getType() {
		return type;
	}
	public void setType(ItemType type) {
		this.type = type;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
