package com.fileconverter.service;

import com.fileconverter.dto.types.DateType;
import com.fileconverter.dto.types.ItemType;
import com.fileconverter.dto.types.NumberType;
import com.fileconverter.dto.types.StringType;
import com.fileconverter.util.BLException;

public class ItemTypeFc {
	public static ItemType getType(String typeStr) throws BLException {
		String type = typeStr.toLowerCase().trim();
		switch (type) {
		case "string":
			return new StringType();
		case "numeric":
			return new NumberType();
		case "date":
			return new DateType();
		default:
			throw new BLException("Unsupported data type exception");
		}
	}
}
