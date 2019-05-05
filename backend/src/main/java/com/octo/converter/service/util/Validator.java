package com.octo.converter.service.util;

import com.octo.converter.service.ConverterException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class Validator {
    public void validateInput(List<File> files) throws ConverterException {
        if(files.size()<2){
          throw new ConverterException(Constants.ExceptionConstants.INSUFFICIENT_FILES);
        }
        if(!(FileUtils.getExtension(files.get(0).getName()).contains(Constants.CSV_EXTENSION)||FileUtils.getExtension(files.get(1).getName()).contains(Constants.CSV_EXTENSION))){
            throw new ConverterException(Constants.ExceptionConstants.NO_METADATA_FOUND);
        }
        if(FileUtils.isEmpty(files.get(0))){
            throw new ConverterException(Constants.ExceptionConstants.EMPTY_FILE+files.get(0).getName());
        }
        if(FileUtils.isEmpty(files.get(1))){
            throw new ConverterException(Constants.ExceptionConstants.EMPTY_FILE+files.get(1).getName());
        }
    }
    public void validateColumnContentForType(String content,String type,Boolean allowAllSpecialChars) throws ConverterException {
        switch(type.toLowerCase()){ //incase if type got different cases like String, DATE, nuMeric
            case Constants.DATE_TYPE:{
                //validation happens in conversion
                return;

            }
            case Constants.STRING_TYPE:{
                if(allowAllSpecialChars) return; //case where string is inside double quotes
                // decimal point not allowed considering it be special character if specified as string.
                if(content.contains(Constants.dot)){
                    throw new ConverterException(Constants.ExceptionConstants.UNSUPPORTED_CHAR+content);
                }
                return;
            }
            case Constants.NUMERIC_TYPE:{
                try {
                    Double num = Double.parseDouble(content);
                } catch (NumberFormatException e) {
                   throw new ConverterException(Constants.ExceptionConstants.UNSUPPORTED_NUMBER_FORMAT+content);
                }
                return;
            }
            default:{
                throw new ConverterException(Constants.ExceptionConstants.UNSUPPORTED_COLUMN_TYPE+ type);
            }
        }
    }
}
