package com.trips.ankur.fffc.metadata;

import com.trips.ankur.fffc.columns.Column;

import lombok.Data;


/**
 * MetaData 
 * 
 * @author tripaank
 *
 */
@Data
public class MetaData {
	private Column[] columns;


	/**
	 * Returns the total Possible of the line from the Metdata file.
	 * @return
	 */
	public int returnTotalLineLength() {
		int totalLineLenght=0;
		for(Column column : this.columns) {
			totalLineLenght+=column.getColumnLength();
		}
		return totalLineLenght;
	}

}
