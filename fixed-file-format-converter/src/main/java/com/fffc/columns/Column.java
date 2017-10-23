package com.fffc.columns;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Represents a column of a metadata file.
 */
public class Column {
	
//	public static final String EMPTY_VALUE = "";

	private static final SimpleDateFormat INPUT = new SimpleDateFormat("yyyy-mm-dd");
	private static final SimpleDateFormat OUTPUT = new SimpleDateFormat("dd/mm/yyyy");

	private static Map<String, DataType> STRING_TO_DATA_TYPE = new HashMap<>();
	
	
	/*
	 * If another data type for a column appears later 
	 * add the definition here and provide the function to parse and print it.
	 */
	private enum DataType {
		
		DATE("date", (dateStr) -> {
			try {
				return OUTPUT.format(INPUT.parse(dateStr));
			} catch (ParseException e) {
				throw new DataFormatException("Cannot parse date:" + dateStr, e);
			}
		}),
		
		STRING("string", (str) -> str),
		
		NUMERIC("numeric", (numStr) -> {
			try {
				//we could as well just return the numStr, 
				//but .2132 is a well defined decimal 
				//and will appear as is and not as 0.2132
				String result = Double.valueOf(numStr).toString();
				return result;
			} catch (NumberFormatException e) {
				//cannot parse number.
				throw new DataFormatException("Cannot parse number:" + numStr, e);
			}
		});
		
		private Function<String, String> converter;
		private String repr;
		
		private DataType(String repr, Function<String, String> converter) {
			this.converter = converter;
			this.repr = repr;
		}
		
	}
	
	private String name;
	private int length;
	private DataType type;
	
	static {
		for(DataType dt : DataType.values()) {
			STRING_TO_DATA_TYPE.put(dt.repr, dt);
		}
	}
	
	private Column(String name, int length, DataType type) {
		this.name = name;
		this.length = length;
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	
	public DataType getType() {
		return type;
	}

	public int length() {
		return length;
	}
	
	public static Column create(String line) {
		String[] args = line.trim().split(",");
		return new Column(args[0], Integer.valueOf(args[1]), STRING_TO_DATA_TYPE.get(args[2]));
	}
	
	public String convert(String value) {
		return type.converter.apply(value);
	}
	
}
