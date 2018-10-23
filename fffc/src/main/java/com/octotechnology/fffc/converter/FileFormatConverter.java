package com.octotechnology.fffc.converter;

import com.octotechnology.fffc.exception.FixedFileFormatException;

/**
 * File format converter contract. 
 * File format converters need to implement these contract methods. 
 *   
 * @author roshith
 *
 */

public interface FileFormatConverter {
	
	/**
	 * Gather meta data and input file details 
	 */
	void initialize() throws FixedFileFormatException;
	
	/**
	 * Convert file based on metadata
	 * @throws FixedFileFormatException
	 */
	void convert() throws FixedFileFormatException;
	
	/**
	 * Clean up resources.
	 */
	void cleanup();
}
