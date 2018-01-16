package octo.test;

public class CsvColumn {

	private String fieldName;
	
	private int length;
	
	private String format;

	public String getFieldName() {
		return fieldName;
	}
	
	public CsvColumn() {}
	
	public CsvColumn(String fieldName, int length, String format) {
		this.fieldName = fieldName;
		this.length = length;
		this.format = format;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
	
	
}
