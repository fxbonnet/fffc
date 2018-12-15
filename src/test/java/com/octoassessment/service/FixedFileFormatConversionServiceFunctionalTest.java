package com.octoassessment.service;

import com.octoassessment.configuration.AppConfig;
import com.octoassessment.model.ConversionParams;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FixedFileFormatConversionServiceFunctionalTest {

    private FixedFileFormatConversionService fixedFileFormatConversionService;
    private ApplicationContext context;

    @BeforeSuite
    public void init() {
        context = new AnnotationConfigApplicationContext(AppConfig.class);
        fixedFileFormatConversionService = (FixedFileFormatConversionService) context.getBean("fixedFileFormatConversionService");
    }

    @Test(dataProvider = "inputDataFiles")
    public void testFixedFileFormatConversion(ConversionParams conversionParams, List<String> expectedOuput) throws Exception {
        fixedFileFormatConversionService.process(conversionParams);
        Assert.assertTrue(Files.readAllLines(Paths.get(conversionParams.getDestinationPath())).containsAll(expectedOuput));
    }

    @DataProvider(name = "inputDataFiles")
    public Object[][] getTestData() throws Exception{
        return new Object[][]{{new ConversionParams(getFilePath("testData/set1/inputSet1.txt"),
                "testOutput.csv", getFilePath("testData/set1/metaDataSet1.txt")),
                Files.readAllLines(Paths.get(getFilePath("testData/set1/expectedOutputSet1.csv")))},

                {new ConversionParams(getFilePath("testData/set2/inputSet2.txt"),
                        "testOutput.csv", getFilePath("testData/set2/metaDataSet2.txt")),
                        Files.readAllLines(Paths.get(getFilePath("testData/set2/expectedOutputSet2.csv")))},

                {new ConversionParams(getFilePath("testData/set3/inputSet3.txt"),
                        "testOutput.csv", getFilePath("testData/set3/metaDataSet3.txt")),
                        Files.readAllLines(Paths.get(getFilePath("testData/set3/expectedOutputSet3.csv")))},

                {new ConversionParams(getFilePath("testData/set4/inputSet4.txt"),
                        "testOutput.csv", getFilePath("testData/set4/metaDataSet4.txt")),
                        Files.readAllLines(Paths.get(getFilePath("testData/set4/expectedOutputSet4.csv")))},

                {new ConversionParams(getFilePath("testData/set5/inputSet5.txt"),
                        "testOutput.csv", getFilePath("testData/set5/metaDataSet5.txt")),
                        Files.readAllLines(Paths.get(getFilePath("testData/set5/expectedOutputSet5.csv")))},

                {new ConversionParams(getFilePath("testData/set6/inputSet6.txt"),
                        "testOutput.csv", getFilePath("testData/set6/metaDataSet6.txt")),
                        Files.readAllLines(Paths.get(getFilePath("testData/set6/expectedOutputSet6.csv")))},

                {new ConversionParams(getFilePath("testData/set7/inputSet7.txt"),
                        "testOutput.csv", getFilePath("testData/set7/metaDataSet7.txt")),
                        Files.readAllLines(Paths.get(getFilePath("testData/set7/expectedOutputSet7.csv")))}

        };
    }

    @AfterSuite
    public void destroy() {
        if (context != null) {
            ((ConfigurableApplicationContext) context).close();
        }
    }

    private String getFilePath(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        return file.getAbsolutePath();
    }

}
