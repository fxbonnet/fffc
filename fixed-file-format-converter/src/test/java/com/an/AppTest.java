package com.an;

import com.an.service.FileProcessorService;
import com.an.service.FileProcessorServiceImpl;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;

/**
 * Unit test for simple App.
 */
public class AppTest
{

    @org.junit.Test
    public void testFile(){

        FileProcessorService fileProcessorService = new FileProcessorServiceImpl();
        //fileProcessorService.processFile();


    }
}
