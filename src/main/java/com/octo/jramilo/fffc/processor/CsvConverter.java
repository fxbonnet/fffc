package com.octo.jramilo.fffc.processor;

import java.util.List;

import com.octo.jramilo.fffc.MetadataDescriptor;
import com.octo.jramilo.fffc.exception.InvalidFormatException;
import com.octo.jramilo.fffc.field.DateField;
import com.octo.jramilo.fffc.field.Field;
import com.octo.jramilo.fffc.field.NumericField;
import com.octo.jramilo.fffc.field.StringField;
import com.octo.jramilo.fffc.model.Metadata;

public class CsvConverter {
	
	public static String covert(MetadataDescriptor descriptor, String line) throws InvalidFormatException {
		List<Metadata> metadataList = descriptor.getMetadataList();
		StringBuffer sb = new StringBuffer();
		
		int lastIndex = 0;
		for(int i = 0; i < metadataList.size(); i++) {
			Metadata meta = metadataList.get(i);
			int metaLength = meta.getLength();
			
			String comma = ",";
			if((lastIndex + metaLength) > line.length()) {
				metaLength = line.length() - lastIndex;
				comma = "";
			}
			
			String parsedData = line.substring(lastIndex, metaLength + lastIndex);
			lastIndex += metaLength;
			
			Field field;
			String type = meta.getType();
			if(type.equalsIgnoreCase("string")) {
				field = new StringField(parsedData);
			} else if(type.equalsIgnoreCase("numeric")) {
				field = new NumericField(parsedData);
			} else if(type.equalsIgnoreCase("date")) {
				field = new DateField(parsedData);
			} else {
				throw new InvalidFormatException("Unknown metadata type!");
			}
			
			sb.append(field.format() + comma);
		}
		
		return sb.toString();
	}

}
