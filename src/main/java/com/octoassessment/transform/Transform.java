package com.octoassessment.transform;

public interface Transform<T,V,P> {

    V apply(T val, P param);
}
