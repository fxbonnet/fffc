package com.octo.converter;


import com.octo.converter.config.WebSecurityConfiguration;
import com.octo.converter.service.ConverterException;
import com.octo.converter.service.ConverterService;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@Import(WebSecurityConfiguration.class)
public class CsvConverterTest {


    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    ConverterService converterService;

    private MockMvc mvc;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testValidFileInput() throws Exception {
        String parent=System.getProperty("user.dir");
       File ffile=new File(parent+"/src/test/resources/positiveCase/input");
       File metadata=new File(parent+"/src/test/resources/metadata.csv");
       File expectedResult=new File(parent+"/src/test/resources/positiveCase/output.csv");
       File actualResult=converterService.convert(ffile,metadata);
       assertTrue(FileUtils.contentEquals(expectedResult,actualResult));
    }

    @Test(expected = ConverterException.class)
    public void testInvalidDateHandling() throws Exception {
        String parent=System.getProperty("user.dir");
        File ffile=new File(parent+"/src/test/resources/negativeCase/invalidDate");
        File metadata=new File(parent+"/src/test/resources/metadata.csv");
        converterService.convert(ffile,metadata);
    }

    @Test(expected = ConverterException.class)
    public void testInvalidStringHandling() throws Exception {
        String parent=System.getProperty("user.dir");
        File ffile=new File(parent+"/src/test/resources/negativeCase/invalidString");
        File metadata=new File(parent+"/src/test/resources/metadata.csv");
        converterService.convert(ffile,metadata);
    }

    @Test(expected = ConverterException.class)
    public void testInvalidNumberHandling() throws Exception {
        String parent=System.getProperty("user.dir");
        File ffile=new File(parent+"/src/test/resources/negativeCase/invalidNumber");
        File metadata=new File(parent+"/src/test/resources/metadata.csv");
        converterService.convert(ffile,metadata);
    }
}
