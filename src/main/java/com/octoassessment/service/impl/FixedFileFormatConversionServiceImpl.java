package com.octoassessment.service.impl;

import com.octoassessment.exception.FixedFileFormatConversionException;
import com.octoassessment.model.ConversionParams;
import com.octoassessment.processor.FileProcessor;
import com.octoassessment.processor.FileReader;
import com.octoassessment.processor.FileWriter;
import com.octoassessment.service.FixedFileFormatConversionService;
import com.octoassessment.service.MetaDataService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class FixedFileFormatConversionServiceImpl<T, V, M> implements FixedFileFormatConversionService, ApplicationContextAware {

    @Autowired
    private FileProcessor<T, V, M> fileProcessor;
    @Autowired
    private MetaDataService<M> metaDataService;

    ApplicationContext applicationContext;

    @Override
    public void process(ConversionParams params) throws FixedFileFormatConversionException {

        FileReader<T> fileReader = (FileReader<T>) applicationContext.getBean("fileReader");
        FileWriter<V, M> fileWriter = (FileWriter<V, M>) applicationContext.getBean("fileWriter");

        fileReader.setSourcePath(params.getSourcePath());
        try {
            M metaData = metaDataService.readMetadata(params.getMetadataPath());
            fileReader.initialize();
            fileWriter.initialize(params.getDestinationPath(),metaData);
            while (fileReader.hasMoreToRead()) {
                T input = fileReader.read();
                V output = fileProcessor.process(input, metaData);
                fileWriter.write(output, params.getDestinationPath(), metaData);

            }
        } catch (Exception ex) {
            throw new FixedFileFormatConversionException(ex);
        } finally {
            fileWriter.closeResources();
            fileReader.closeResouces();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
