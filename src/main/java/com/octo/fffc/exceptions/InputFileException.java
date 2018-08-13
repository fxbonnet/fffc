package com.octo.fffc.exceptions;

/**
 * @author alanterriaga
 * @project fffc
 */
public class InputFileException extends Exception{

    private static final String message = "Error: Input file has not the appropriate format";

    public InputFileException(){
        super(message);
    }
}
