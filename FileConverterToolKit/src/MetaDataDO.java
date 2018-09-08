import java.util.LinkedHashMap;
import java.util.Map;


public class MetaDataDO {
	private Map<String,Integer> columnDataLengthMap = new LinkedHashMap<String,Integer>();
	private Map<String,String> columnDataTypeMap = new LinkedHashMap<String,String>();
	private int totalLengthPerLine;
	
	
	public Map<String, Integer> getColumnDataLengthMap() {
		return columnDataLengthMap;
	}
	public void setColumnDataLengthMap(Map<String, Integer> columnDataLengthMap) {
		this.columnDataLengthMap = columnDataLengthMap;
	}
	public Map<String, String> getColumnDataTypeMap() {
		return columnDataTypeMap;
	}
	public void setColumnDataTypeMap(Map<String, String> columnDataTypeMap) {
		this.columnDataTypeMap = columnDataTypeMap;
	}
	public int getTotalLengthPerLine() {
		return totalLengthPerLine;
	}
	public void setTotalLengthPerLine(int totalLengthPerLine) {
		this.totalLengthPerLine = totalLengthPerLine;
	}
	
	
	
	
}
