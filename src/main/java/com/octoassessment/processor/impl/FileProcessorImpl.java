package com.octoassessment.processor.impl;

import com.octoassessment.model.Line;
import com.octoassessment.model.Metadata;
import com.octoassessment.processor.FileProcessor;
import com.octoassessment.util.FormatConvertHelper;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FileProcessorImpl implements FileProcessor<List<String>, List<Line>, Metadata> {

    @Override
    public List<Line> process(List<String> input, Metadata metadata) {
        return Optional.ofNullable(input).map( t -> t.stream().map(l -> FormatConvertHelper.toLine(l, metadata))
                .filter(v -> v!=null)
                .collect(Collectors.toList())).orElse(Collections.emptyList());
    }

}
