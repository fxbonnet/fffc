package com.octo.au;

import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
/**
 * Chain pattern tests.
 *
 * @author Amol Kshirsagar
 */
@Test(enabled = true)
public class FileFormatConverterServiceTest extends TestSupport {

	private static final Logger LOG = LoggerFactory.getLogger(FileFormatConverterServiceTest.class);
	@BeforeClass
	public void setup() {
		
	}
	/**
	 * Tests a support request that can not resolved by an 'old-way' support chain.
	 */
	@Test(expectedExceptions = {UnsupportedRequestException.class})
	public void testValidateInput() {
		//data file checks
		//Date format check
		//no thousands seperator check
		
		//meta data file checks
		//mismatch between data file and metadata file column numbers
		//if the metadata file contains columns whose type is not String/Date/number
		
		
		/*You should transform the file to a csv file (separator ',' and row separator CRLF)
		The dates have to be reformatted to dd/mm/yyyy
		The trailing spaces of string columns must be trimmed
		The csv file must include a first line with the columns names*/
		
		/*files are encoded in UTF-8 and may contain special characters
		strings columns may contain separator characters like ',' and then the whole string needs to be escaped with " (double quotes). Only CR or LF are forbidden
		in case the format of the file is not correct, the program should fail but say explicitly why
		a fixed format file may be very big (several GB)*/
		
		//cover all the null check scenarios
	
		//VALIDATE LOGIC SHOULD BE BEFORE YOUR DATA READING LOGIC
	}
}
