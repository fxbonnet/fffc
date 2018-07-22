/**
 * 
 */
package com.octo.au.domain.model;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.octo.au.domain.service.builder.RowBuilder;

/**
 * @author Amol Kshirsagar
 *
 */
public class DataRow extends Row{
	private static final Logger LOG = LoggerFactory.getLogger(RowBuilder.class);
	private List<DataColumn> columns = new ArrayList<DataColumn>();;
	/* (non-Javadoc)
	 * @see com.octo.au.domain.model.Row#rowIndex()
	 */
	
	
	   public void addColumn(DataColumn col){
		   columns.add(col);
	   }
	   
	   public void addColumns(List<DataColumn> cols){
		   columns.addAll(cols);
	   }

	   public void showItems(){
	      for (DataColumn col : columns) {
	    	 LOG.info("Index : " + col.getColumnIndex());
	    	 LOG.info(", Value : " + col.getValue());
	    	 LOG.info(", Length : " + col.getLength());
	    	 LOG.info(", Type : " + col.getType());
	      }		
	   }

	public List<DataColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<DataColumn> columns) {
		this.columns = columns;
	}	

}
