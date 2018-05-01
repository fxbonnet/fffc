package com.mrdaydreamer.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mrdaydreamer.model.DataRecord;
import com.mrdaydreamer.model.MetaInfo;

public class DataParser {

	private List<MetaInfo> metaList;
		
	public DataParser(List<MetaInfo> metaList) {
		this.metaList = new ArrayList<>(metaList);
	}
	
	public DataRecord parse(String rawDataLine) throws ParsingException {
		DataRecord dataRecord = new DataRecord();
		int startIndex = 0;
		try {
			String chunk;
			for(int i = 0; i < metaList.size(); i++) {
				MetaInfo meta = metaList.get(i);
				
				int endIndex = startIndex + meta.getColumnWidth();
				chunk = rawDataLine.substring(startIndex, endIndex);
				startIndex = endIndex;
				
				switch(meta.getDataType()) {
					case "string":
						dataRecord.appendField(chunk.trim());
						break;
					case "numeric":
						dataRecord.appendField(Double.valueOf(chunk).toString());
						break;
					case "date":
						DateFormat sourceFormat = new SimpleDateFormat("yyyy-mm-dd");
						Date parsedDate = sourceFormat.parse(chunk);
						DateFormat targetFormat = new SimpleDateFormat("dd/mm/yyyy");
						dataRecord.appendField(targetFormat.format(parsedDate));
						break;
					default:
						throw new UnknownMetaException();
				}
			}
		} catch(NumberFormatException e) {
			throw new ParsingException("Invalid number input on position " + startIndex + " of the line: " + rawDataLine, e);
		} catch (UnknownMetaException e) {
			throw new ParsingException("Unknown data type on position " + startIndex + " of the line: " + rawDataLine, e);
		} catch (ParseException e) {
			throw new ParsingException("Invalid date input on position " + startIndex + " of the line: " + rawDataLine, e);
		}
		return dataRecord;
	}
	
	public int fieldCount() {
		return metaList.size();
	}
}
