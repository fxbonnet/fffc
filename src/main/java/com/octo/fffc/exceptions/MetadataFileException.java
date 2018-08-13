package com.octo.fffc.exceptions;

/**
 * @author alanterriaga
 * @project FFFC
 */
public class MetadataFileException extends Exception{

    private static final String message = "Error: Metadata file has not the appropriate format";

    public MetadataFileException(){
        super(message);
    }
}
