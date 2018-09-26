package com.octo.parser.columntype;

public interface TypeParser<T> {

    T parse(String value);
}
