package com.octo.jramilo.fffc.util;

import java.util.List;

import com.octo.jramilo.fffc.MetadataDescriptor;
import com.octo.jramilo.fffc.exception.InvalidFormatException;
import com.octo.jramilo.fffc.field.Field;
import com.octo.jramilo.fffc.field.FieldFactory;
import com.octo.jramilo.fffc.model.Metadata;

public class CsvConverter {
	
	public static String covert(MetadataDescriptor descriptor, String line) throws InvalidFormatException {
		List<Metadata> metadataList = descriptor.getMetadataList();
		String[] formattedFields = new String[metadataList.size()];
		
		int offset = 0;
		int idx = 0;
		for(Metadata meta : metadataList) {
			int metaLength = meta.getLength();
			
			if((offset + metaLength) > line.length()) {
				metaLength = line.length() - offset;
			}
			
			String parsedData = line.substring(offset, metaLength + offset);
			offset += metaLength;
			
			Field field = FieldFactory.getField(meta.getType());
			formattedFields[idx++] = field.format(parsedData);
		}
		
		return String.join(Constant.COMMA, formattedFields);
	}

}
