package com.github.mpawlucz.octo.fffc.domain.parser;

import com.github.mpawlucz.octo.fffc.domain.AbstractColumnParser;
import lombok.Builder;
import org.apache.commons.lang3.StringUtils;

public class StringColumnParser extends AbstractColumnParser {

    public static final String TYPE_NAME = "string";

    @Builder
    public StringColumnParser(String name, Integer length) {
        super(name, length);
    }

    @Override
    public Object parse(String raw) {
        return StringUtils.stripEnd(raw, null);
    }

}
