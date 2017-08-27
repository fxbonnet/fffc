/**
 * 
 */
package fffc;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * TODO - Write lots more tests to check for meaningful error messages etc.
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
	 * Check to see that the converter tool generates a meaningful error if meta data file is not found.
	 * 
	 * TODO Assert that error message is correct.
	 */
	@Test
	public void testWithGoodInputFileName() {
		String testFileDirectory = System.getProperty("user.dir") + "/testFiles/";
		String[] args = {testFileDirectory+ "metaDataXX.csv", testFileDirectory + "fixedFile1.txt", testFileDirectory + "outputCsvFile2.csv"};
		FixedFileFormatConverter.main(args);
	}
	
	/**
	 * Check to see that the converter tool generates a meaningful error if input data is bad
	 * TODO Assert that error message is correct.
	 */
	@Test
	public void testWithBadMetaData() {
		String testFileDirectory = System.getProperty("user.dir") + "/testFiles/";
		String[] args = {testFileDirectory+ "metaData1.csv", testFileDirectory + "fixedFileBad.txt", testFileDirectory + "outputCsvFileBad.csv"};
		FixedFileFormatConverter.main(args);
	}
}
