package com.assignment.fffc.processors;

import com.assignment.fffc.model.Column;

import java.util.List;

public interface MetaDataProcessor {

    List<Column> extractMetaData (String metaDatafileName) throws Exception;
}
