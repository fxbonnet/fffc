package com.fileconverter.dto.types;

import org.apache.commons.lang3.StringUtils;

public class StringType implements ItemType {
	@Override
	public String parse(String sourceString) {
		String result = null;
		String item = StringUtils.stripEnd(sourceString, " ");
		if (sourceString.contains(",")) {
			result = "\"" + item + "\"";
		} else {
			result = item;
		}
		return result;
	}

}
