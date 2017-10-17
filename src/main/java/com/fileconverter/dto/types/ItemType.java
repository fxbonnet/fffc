package com.fileconverter.dto.types;

import com.fileconverter.util.BLException;

public interface ItemType {
	String parse(String str) throws BLException;
}
