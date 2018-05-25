package com.octo.fffc.metadata;

import com.octo.fffc.Configurator;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

    @Bean
    public ColumnDefinitionExtractor getColumnDefExtractor() {
        Configurator config = Mockito.mock(Configurator.class);
        return new ColumnDefinitionExtractor(config);
    }

//    @Bean
//    public Configurator getConfigurator() {
//        return Mockito.mock(Configurator.class);
//    }
}
