package com.octo.fffc.exceptions;

/**
 * @author alanterriaga
 * @project FFFC
 */
public class FileLocationNotFoundException extends Exception {

    private static final String message = "Error: File location not found, not possible to convert the File";

    public FileLocationNotFoundException(){
        super(message);
    }

}
