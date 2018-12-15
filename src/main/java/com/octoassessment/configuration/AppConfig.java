package com.octoassessment.configuration;

import com.octoassessment.model.Metadata;
import com.octoassessment.processor.FileReader;
import com.octoassessment.processor.FileWriter;
import com.octoassessment.processor.impl.FileProcessorImpl;
import com.octoassessment.processor.impl.FileReaderImpl;
import com.octoassessment.processor.impl.FileWriterImpl;
import com.octoassessment.service.FixedFileFormatConversionService;
import com.octoassessment.service.impl.FixedFileFormatConversionServiceImpl;
import com.octoassessment.service.MetaDataService;
import com.octoassessment.service.impl.MetaDataServiceImpl;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.List;

@Configuration
public class AppConfig {

    @Bean
    public FileProcessorImpl getFileProcessor(){
        return new FileProcessorImpl();
    }

    @Bean(name = "fileReader")
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public FileReader<List<String>> getFileReader(){
        return new FileReaderImpl();
    }

    @Bean(name = "fileWriter")
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public FileWriter getFileWriter(){
        return new FileWriterImpl();
    }


    @Bean(name = "fixedFileFormatConversionService")
    public FixedFileFormatConversionService getFixedFileFormatConversinService(){
        return new FixedFileFormatConversionServiceImpl();
    }

    @Bean(name = "metaDataService")
    public MetaDataService<Metadata> getMetaData(){
        return new MetaDataServiceImpl();
    }
}
