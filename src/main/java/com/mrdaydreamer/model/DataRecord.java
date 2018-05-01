package com.mrdaydreamer.model;

import java.util.ArrayList;
import java.util.List;

public class DataRecord {

	final private static String FIELD_SEPERATOR = ",";
	
	private List<String> fields = new ArrayList<>();
	
	public void appendField(String field) {
		fields.add(field);
	}
	
	public int size() {
		return fields.size();
	}
	
	public String print() {
		StringBuffer buffer = new StringBuffer();
		for(int i = 0; i < fields.size(); i++) {
			buffer.append(fields.get(i));
			if(i < fields.size() - 1)
				buffer.append(FIELD_SEPERATOR);
		}
		return buffer.toString();
	}
}
