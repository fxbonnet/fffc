package exception;

/**
 * Enum for Errors. 
 * Mainly used for classification of an error scenario.
 *  
 * @author jajalvm
 *
 */
public enum ErrorLevel {
	FATAL, 
	ERROR, 
	WARNING;
	
	public ErrorLevel getErrorLevel(final String errorLevel) {
		// default error level
		ErrorLevel level = ErrorLevel.ERROR;	
		switch(errorLevel.toUpperCase()){
		case "FATAL":
			level = ErrorLevel.FATAL;
			break;
		case "WARNING":
			level = ErrorLevel.WARNING;
			break;
		}
		
		return level;
	}
}
