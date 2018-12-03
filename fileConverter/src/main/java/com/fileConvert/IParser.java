package com.fileConvert;

import com.exception.CustomException;

public interface IParser {

	public void readWriteFile(String textFilePath, String csvFilePath,String metaDataFilePath) throws CustomException;

}
