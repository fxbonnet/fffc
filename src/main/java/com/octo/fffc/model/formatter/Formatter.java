package com.octo.fffc.model.formatter;

import com.octo.fffc.exception.ParseErrorException;

interface Formatter {

    String format(String input) throws ParseErrorException;
}
