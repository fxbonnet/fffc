package com.octoassessment.processor;

public interface FileProcessor<T,V,M> {
     V process(T input, M metadata);
}
