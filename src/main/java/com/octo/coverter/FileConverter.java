package com.octo.coverter;

import com.octo.config.AppConfig;
import com.octo.parser.ColumnTypeParserFactory;

public interface FileConverter<T extends Object> {

    void init();

    boolean convert();

    void cleanUp();

    ColumnTypeParserFactory<T> getColumnTypeParserFactory();

    AppConfig getAppConfig();


}
