package com.trips.ankur.fffc.exceptions;

/**
 * 
 * @author tripaank
 *
 */

public class InvalidLineFormatException extends RuntimeException{

    /**
	 * 
	 */
	private static final long serialVersionUID = -8287525335521358322L;

	public InvalidLineFormatException(String message) {
        super(message);
    }
}
