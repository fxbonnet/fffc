package com.octo.fffcapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.octo.fffcapp.converter.FFFConverter;
import com.octo.fffcapp.file.FileManager;

@RunWith(MockitoJUnitRunner.class)
public class FFFConverterTest {
	@Mock
	FileManager fileManager;
	
	@InjectMocks
	FFFConverter converter;
	
	@Test
	public void validFileConversionTest() {
		when(fileManager.getInputFilePath()).thenReturn(Paths.get("files/input/data.txt"));
		when(fileManager.getMetadataFilePath()).thenReturn(Paths.get("files/input/metadata.txt"));
		when(fileManager.getOutputFilePath()).thenReturn(Paths.get("files/output/output.csv"));
		
		converter.process();
		
		try {
			List<String> lines = Files.readAllLines(Paths.get("files/output/output.csv"));
			
			assertEquals("Birth date,First name,Last name,Weight", lines.get(0));
			assertEquals("01/01/1970,John,Smith,81.5", lines.get(1));
			assertEquals("31/01/1975,Jane,Doe,61.1", lines.get(2));
			assertEquals("28/11/1988,Bob,Big,102.4", lines.get(3));			
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void invalidFileLocationTest() {
//		when(fileManager.getInputFilePath()).thenReturn(Paths.get("invalid"));
		when(fileManager.getMetadataFilePath()).thenReturn(Paths.get("invalid"));
//		when(fileManager.getOutputFilePath()).thenReturn(Paths.get("invalid"));
		
		assertFalse(converter.process());
	}
}
