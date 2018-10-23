package com.octotechnology.fffc;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * 
 * Test class to unit test FixedFileFormatConverter
 * 
 * @author roshith
 *
 */

@RunWith(MockitoJUnitRunner.class)
public class FileFormatConverterAppTest {
	FileFormatConverterApp fileFormatConverterApp =  new FileFormatConverterApp();
	
	@Test
	public void testValidateArgs() {
		String[] args1 = { "inputfilename", "metadatafilename" };
		assertFalse(FileFormatConverterApp.validateArgs(args1));
		
		String[] args2 = { "inputfilename", "metadatafilename", "outputfilename" };
		assertTrue(FileFormatConverterApp.validateArgs(args2));
	}
}
