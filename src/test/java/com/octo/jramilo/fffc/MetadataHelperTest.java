package com.octo.jramilo.fffc;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.octo.jramilo.fffc.exception.InvalidFileExpection;
import com.octo.jramilo.fffc.exception.InvalidFormatException;
import com.octo.jramilo.fffc.model.Metadata;

import static org.junit.Assert.*;

public class MetadataHelperTest {

	@Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void nominalCase() throws IOException, InvalidFileExpection, InvalidFormatException {
        File file = folder.newFile("metadata.txt");
        PrintWriter writer = new PrintWriter(file, "UTF-8");
        writer.write("Birthdate,10,date\n");
        writer.write("Firstname,10,String\n");
        writer.write("Lastname,10,String\n");
        writer.write("Weight,5,numeric\n");
        writer.close();
        
        MetadataDescriptor descriptor = MetadataHelper.INSTANCE.describe(file);
        
        List<Metadata> metaList = descriptor.getMetadataList();
        assertEquals(4, metaList.size());
        
        Metadata birthdataMeta = metaList.get(0);
        assertEquals("Birthdate", birthdataMeta.getName());
        assertEquals(10, birthdataMeta.getLength());
        assertEquals("date", birthdataMeta.getType());
        
        Metadata firstnameMeta = metaList.get(1);
        assertEquals("Firstname", firstnameMeta.getName());
        assertEquals(10, firstnameMeta.getLength());
        assertEquals("String", firstnameMeta.getType());
        
        Metadata lastnameMeta = metaList.get(2);
        assertEquals("Lastname", lastnameMeta.getName());
        assertEquals(10, lastnameMeta.getLength());
        assertEquals("String", lastnameMeta.getType());
        
        Metadata weightMeta = metaList.get(3);
        assertEquals("Weight", weightMeta.getName());
        assertEquals(5, weightMeta.getLength());
        assertEquals("numeric", weightMeta.getType());
    }
	
    
    @Test(expected = InvalidFormatException.class)
    public void invalidMetaFile() throws IOException, InvalidFileExpection, InvalidFormatException {
    	File file = folder.newFile("metadata.txt");
        PrintWriter writer = new PrintWriter(file, "UTF-8");
        writer.write("Birthdate,10,,date\n");
        writer.close();
        
        MetadataHelper.INSTANCE.describe(file);
    }
    
    @Test(expected = InvalidFileExpection.class) 
    public void nullMetadataFile() throws IOException, InvalidFileExpection, InvalidFormatException {
    	MetadataHelper.INSTANCE.describe(null);
    }
    
    @Test
    public void emptyMetaFile() throws IOException, InvalidFileExpection, InvalidFormatException {
    	File file = folder.newFile("metadata.txt");
        
    	MetadataDescriptor descriptor = MetadataHelper.INSTANCE.describe(file);
        
        List<Metadata> metaList = descriptor.getMetadataList();
        assertEquals(0, metaList.size());
    }
}
