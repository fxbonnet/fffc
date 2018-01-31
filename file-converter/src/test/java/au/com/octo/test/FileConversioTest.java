package au.com.octo.test;

import org.junit.Test;

import au.com.octo.FileConversion;
import au.com.octo.beans.FileConversionBean;
import au.com.octo.util.helper.FileConversionHelper;

public class FileConversioTest {

	private static final String baseLocation = "src/test/resources/";
	
	@Test
	public void testSimpleScenario() {
		String scenario = "simple";
		
		String dataFixedFormatFile = FileConversionHelper.getFixedFormatDataFileName(scenario, baseLocation);
		String metadataFile = FileConversionHelper.getMetaDataFileName(scenario, baseLocation);
		String outputCSVFormatFile = FileConversionHelper.getOutputFileName(scenario, baseLocation);
		
		
		FileConversionBean files = new FileConversionBean(dataFixedFormatFile, metadataFile, outputCSVFormatFile);
		
		new FileConversion().convertFixedFormatToCSV(files);
		
	}
	
}
