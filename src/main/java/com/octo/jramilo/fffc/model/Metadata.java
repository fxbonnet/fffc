package com.octo.jramilo.fffc.model;

public class Metadata {
	private String name;
	private int length;
	private String type;
	
	public Metadata(String name, int length, String type) {
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
	
	public String getType() {
		return type;
	}
}
