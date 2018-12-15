package com.octoassessment.transform.impl;

import com.octoassessment.model.ColumnMetaData;
import com.octoassessment.transform.Transform;

public class CommaRemover implements Transform<String,String, ColumnMetaData> {
    private final String COMMA = ",";

    @Override
    public String apply(String val, ColumnMetaData columnMetaData) {
        if(val.contains(COMMA)){
            return "\""+val+"\"";
        }
        return val;
    }
}
