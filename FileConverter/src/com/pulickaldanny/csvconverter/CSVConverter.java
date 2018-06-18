package com.pulickaldanny.csvconverter;

import java.io.File;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JProgressBar;

/* This class create objects and call method to process 
 * meta data file and input data file*/
public class CSVConverter {
	public void process(File metaFile, File inputFile, File outputFile, JLabel jl, JProgressBar jp) throws Exception {
		/*Creates object for verifying and collecting meta data file information */
		MetaDataCollector mdc = new MetaDataCollector();
		/* Updating the status text in progress GUI*/
		jl.setText("Verifing and collecting meta data....");
		/*Calling method to verify and process meta data file
		 * Input: meta data file
		 * Output: List of meta data*/
		List<MetaData> mdl = mdc.collectMetaDataInfo(metaFile);
		/*Creates object for verifying and collecting input data file information */
		
		DataProcessor dp = new DataProcessor();
		/* Updating the status text in progress GUI*/
		jl.setText("Converting input data into CSV....");
		/*Calling method to verify and process meta data file
		 * Input: List of meta data, input file path, output file path, JProgressBar object 
		 * Output: NA*/
		dp.processData(mdl, inputFile, outputFile, jp);
		/* Updating the status text in progress GUI*/
		jl.setText("Conversion completed....");

	}

}
