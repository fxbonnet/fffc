package com.octo.jramilo.fffc.model;

public enum MetadataColumn {
	NAME(0),
	LENGTH(1),
	TYPE(2);
	
	private int index;
	
	MetadataColumn(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}
}
