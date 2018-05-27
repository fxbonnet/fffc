package com.octo.fffc.converter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Contains all the properties defined in the properties file
 *
 * @author anoop.shiralige
 * @version 1.0.0
 * @since 1.0.0
 */
@Configuration
@ConfigurationProperties(prefix = "octo.fffc")
public class Configurator {
    private String inputDateFormat;
    private String outputDateFormat;
    private String fieldDelimiter;

    public String getInputDateFormat() {
        return inputDateFormat;
    }

    public void setInputDateFormat(String inputDateFormat) {
        this.inputDateFormat = inputDateFormat;
    }

    public String getOutputDateFormat() {
        return outputDateFormat;
    }

    public void setOutputDateFormat(String outputDateFormat) {
        this.outputDateFormat = outputDateFormat;
    }

    public String getFieldDelimiter() {
        return fieldDelimiter;
    }

    public void setFieldDelimiter(String fieldDelimiter) {
        this.fieldDelimiter = fieldDelimiter;
    }
}
