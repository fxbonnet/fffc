/**
 * 
 */
package fffc;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * TODO - Lots more tests to check for meaningful error messages.
 * 
 * @author Alan Bron
 */
public class FixedFileFormatConverterTest {

	/**
	 * Check to see that the converter tool works with error-free input
	 * TODO Add Assert - check output file contents.
	 */
	@Test
	public void testWithGoodInputFiles() {
		String testFileDirectory = System.getProperty("user.dir") + "/testFiles/";
		String[] args = {testFileDirectory+ "metaData1.csv", testFileDirectory + "fixedFile1.txt", testFileDirectory + "outputCsvFile1.csv"};
		FixedFileFormatConverter.main(args);
	}

	/**
	 * Check to see that the converter tool generates a meaningful error if meta data fiule is not found input.
	 * 
	 * TODO Assert that error message is correct.
	 */
	@Test
	public void testWithGoodInputFileName() {
		String testFileDirectory = System.getProperty("user.dir") + "/testFiles/";
		String[] args = {testFileDirectory+ "metaDataXX.csv", testFileDirectory + "fixedFile1.txt", testFileDirectory + "outputCsvFile1.csv"};
		FixedFileFormatConverter.main(args);
	}
	
	/**
	 * Check to see that the converter tool generates a meaningful error if meta data is bad
	 * TODO Determine how to handle this problem.  Add assert statement.
	 */
	@Test
	public void testWithBadMetaData() {
		String testFileDirectory = System.getProperty("user.dir") + "/testFiles/";
		String[] args = {testFileDirectory+ "metaDataBad.csv", testFileDirectory + "fixedFile1.txt", testFileDirectory + "outputCsvFileBad.csv"};
		FixedFileFormatConverter.main(args);
	}
}
