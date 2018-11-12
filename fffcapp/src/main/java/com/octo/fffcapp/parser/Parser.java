package com.octo.fffcapp.parser;

import com.octo.fffcapp.exception.FixedFileFormatConverterParseException;

public interface Parser<T> {
	public void accept(T t) throws FixedFileFormatConverterParseException;
}
