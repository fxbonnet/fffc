package com.github.mpawlucz.octo.fffc.domain;

import lombok.Getter;

import java.util.Optional;

public abstract class AbstractColumnParser {

    @Getter
    private String name;

    @Getter
    private Integer length;

    public AbstractColumnParser(String name, Integer length) {
        this.name = Optional.ofNullable(name).orElseThrow(() -> new RuntimeException("column name is null"));
        this.length = Optional.ofNullable(length).orElseThrow(() -> new RuntimeException("column length is null"));
    }

    public abstract Object parse(String raw);

}
