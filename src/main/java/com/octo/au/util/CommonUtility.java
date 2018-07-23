package com.octo.au.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
/**
 * @author Amol Kshirsagar
 *
 */
public class CommonUtility {
	
	public static String quote(String aText){
	    String QUOTE = "'";
	    return QUOTE + aText + QUOTE;
	  }
	
	public static String dateFormatConverter(String sourceDate,String targetFormat){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate parsedDate = LocalDate.parse(sourceDate, formatter);
        return parsedDate.toString();
	}
}
