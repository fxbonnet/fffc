package com.assignment.fffc.processors;

import com.assignment.fffc.model.Column;

import java.util.List;

public interface DataProcessor {

    String process(String line, List<Column> columns) throws Exception;
}
