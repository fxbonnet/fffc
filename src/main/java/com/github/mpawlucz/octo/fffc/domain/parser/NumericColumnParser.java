package com.github.mpawlucz.octo.fffc.domain.parser;

import com.github.mpawlucz.octo.fffc.domain.AbstractColumnParser;
import lombok.Builder;

import java.math.BigDecimal;

public class NumericColumnParser extends AbstractColumnParser {

    public static final String TYPE_NAME = "numeric";

    @Builder
    public NumericColumnParser(String name, Integer length) {
        super(name, length);
    }

    @Override
    public Object parse(String raw) {
        final String trimmed = raw.trim();
        final BigDecimal bigDecimal = new BigDecimal(trimmed);
        return bigDecimal.toPlainString();
    }

}
