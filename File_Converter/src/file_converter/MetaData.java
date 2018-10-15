/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file_converter;

/**
 *
 * @author Nayeem Hasan
 */
public class MetaData {
    
    String columnName;
	int length;
	String type;
	
	MetaData (String columnName, int lenght, String type) {
		this.columnName = columnName;
		this.length = lenght;
		this.type = type;
	}
    
}
