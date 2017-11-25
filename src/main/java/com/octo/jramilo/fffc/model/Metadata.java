package com.octo.jramilo.fffc.model;

public class Metadata {
	private String name;
	private int length;
	private MetadataType type;
	
	public Metadata(String name, int length, MetadataType type) {
		this.name = name;
		this.length = length;
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	
	public int getLength() {
		return length;
	}
	
	public MetadataType getType() {
		return type;
	}
}
