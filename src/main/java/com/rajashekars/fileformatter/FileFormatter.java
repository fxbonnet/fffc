package com.rajashekars.fileformatter;

import java.io.InputStream;

public interface FileFormatter {
    String convertToCsv(InputStream metadataStream, InputStream dataStream);
}