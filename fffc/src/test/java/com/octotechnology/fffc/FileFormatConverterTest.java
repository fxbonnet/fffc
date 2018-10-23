package com.octotechnology.fffc;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.octotechnology.fffc.converter.FixedFileFormatConverter;
import com.octotechnology.fffc.exception.FixedFileFormatException;

@RunWith(MockitoJUnitRunner.class)
public class FileFormatConverterTest {

	@Mock
	ConfigData configdata;
	@InjectMocks
	FixedFileFormatConverter fixedFileFormatConverter;

	String metaDataFile = System.getProperty("user.dir") + "/src/test/resources/metadata-valid.txt";
	String inputFile = System.getProperty("user.dir") + "/src/test/resources/input_valid.txt";
	String outPutFile = System.getProperty("user.dir") + "/src/test/resources/output.csv";

	
	@Test
	public void testInitialize() {
		String metaDataFile = System.getProperty("user.dir") + "/src/test/resources/metadata-valid.txt";
		when(configdata.getMetaDataFilePath()).thenReturn(metaDataFile);
		try {
			fixedFileFormatConverter.initialize();
		} catch (FixedFileFormatException e) {
			e.printStackTrace();
			fail();
		}
		assert (true);
	}

	@Test
	public void testInitializeInvalidFile() {
		String metaDataFile = System.getProperty("user.dir") + "/src/test/resources/metadata-invalid.txt";
		when(configdata.getMetaDataFilePath()).thenReturn(metaDataFile);
		try {
			fixedFileFormatConverter.initialize();
		} catch (FixedFileFormatException e) {
			assert (true);
		}
	}

	@Test
	public void testConvert() {
		
		String expectedHeader = "Birth date,First name,Last name,Weight";
		String expectedFirstLine = "01/01/1970,John,Smith,81.5";

		when(configdata.getMetaDataFilePath()).thenReturn(metaDataFile);
		when(configdata.getInputFilePath()).thenReturn(inputFile);
		when(configdata.getOutPutFilePath()).thenReturn(outPutFile);

		try {
			fixedFileFormatConverter.initialize();
		} catch (FixedFileFormatException e) {
			e.printStackTrace();
		}
		fixedFileFormatConverter.convert();

		fixedFileFormatConverter.cleanup();
		
		try {
			List<String> lines = Files.readAllLines(Paths.get(outPutFile));
			assertEquals(4, lines.size());
			assertEquals(expectedHeader, lines.get(0));
			assertEquals(expectedFirstLine, lines.get(1));
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@After
    public void cleanup() {
        File file = new File(outPutFile);
        if (file.exists()) {
            file.delete();
        }
    }
}
