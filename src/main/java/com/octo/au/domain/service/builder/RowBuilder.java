/**
 * 
 */
package com.octo.au.domain.service.builder;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.octo.au.domain.model.DataColumn;
import com.octo.au.domain.model.DataRow;

/**
 * @author Amol Kshirsagar
 *
 */
public class RowBuilder {
	private static final Logger LOG = LoggerFactory.getLogger(RowBuilder.class);
	DataRow dataRow;
	public RowBuilder() {
	}
	public DataRow buildDataRow(List<DataColumn> dataCols){
		dataRow = new DataRow();
		dataRow.addColumns(dataCols);
	    return dataRow;
	   }   
	
	 public void showItems(){
	      for (DataColumn col : dataRow.getColumns()) {
	    	  LOG.info("Index : " + col.getColumnIndex());
	    	  LOG.info(", Value : " + col.getValue());
	    	  LOG.info(", Type : " + col.getType());
	    	  LOG.info(", Length : " + col.getLength());
	      }		
	   }
}
