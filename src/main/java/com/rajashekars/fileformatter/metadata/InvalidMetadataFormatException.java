package com.rajashekars.fileformatter.metadata;

import com.rajashekars.fileformatter.FileFormatterException;

public class InvalidMetadataFormatException extends FileFormatterException {
    public InvalidMetadataFormatException(String message) {
        super(message);
    }
}
