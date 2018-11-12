package com.octo.fffcapp.parser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.octo.fffcapp.exception.FixedFileFormatConverterParseException;

public class MetadataFormat {
	public static enum ColumnType{
		DATE {
			@Override
			public String convert(String original) throws FixedFileFormatConverterParseException {
				DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd");
				
				try {
					Date date = originalFormat.parse(original);
					DateFormat convertedFormat = new SimpleDateFormat("dd/MM/yyyy");
					
					return convertedFormat.format(date);
				} catch (ParseException e) {
					throw new FixedFileFormatConverterParseException("Invalid date format.", e);
				}
			}
		}, NUMERIC {
			@Override
			public String convert(String original) throws FixedFileFormatConverterParseException {
				if (original == null || original.equals("")) return "";
				
				original = original.trim();
				if(original.matches("-?\\d+(\\.\\d+)?")) {
					return original;
				} else {
					throw new FixedFileFormatConverterParseException("Invalid number format.");
				}
			}
		},
		STRING {
			@Override
			public String convert(String original) throws FixedFileFormatConverterParseException {
				if(original == null || original.equals("")) return "";
				
				int i = original.length()-1;
				while(i >= 0 && original.charAt(i)==' '){
					i--;
				}
				original = original.substring(0, i+1);
				
				if(original.contains(",")) original = "\"" + original + "\"";
				
				return original;
			}
		};
		
		public abstract String convert(String original) throws FixedFileFormatConverterParseException;
	}
	class Column {
		private String header;
		private int length;
		private ColumnType type;
		
		public Column(String header, int length, ColumnType type) {
			this.header = header;
			this.length = length;
			this.type = type;
		}

		/**
		 * @return the header
		 */
		public String getHeader() {
			return header;
		}

		/**
		 * @return the length
		 */
		public int getLength() {
			return length;
		}

		/**
		 * @return the type
		 */
		public ColumnType getType() {
			return type;
		}
		
		public String toString() {
			return header + "," + length + "," + type;
		}
	}
	
	private List<Column> columns;
	
	private static volatile MetadataFormat instance = null;
	
	private MetadataFormat() {
		columns = new ArrayList<>();
	}
	
	public static MetadataFormat getInstance() {
		if (instance == null) {
            synchronized(MetadataFormat.class) {
                if (instance == null) {
                    instance = new MetadataFormat();
                }
            }
        }

        return instance;
	}
	
	public int getColumnCount() {
		return columns.size();
	}
	
	public Column getColumn(int i) {
		return columns.get(i);
	}
	
	public void clear() {
		columns.clear();
	}
	
	public void addColumn(Column column) {
		columns.add(column);
	}
}
