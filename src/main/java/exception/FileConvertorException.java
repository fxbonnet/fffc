package exception;

public class FileConvertorException extends Exception {

	/**
	 * Application-wide exception. Use this exception for exception handling.
	 */
	private static final long serialVersionUID = -5832884578626410639L;

	private int code; // for any custom error codes
	private ErrorLevel errorLevel; // for FATAL, WARNING,  
	private String message; // for user friendly message, can be from the resource file for internationalisation.
	
	public FileConvertorException() {
	}

	public FileConvertorException(String message) {
		super(message);
		this.message = message;
	}

	public FileConvertorException(final int code, final String message) {
		super(message);
		this.code = code;
		this.message = message;
	}

	public FileConvertorException(final ErrorLevel errorLevel, final String message) {
		super(message);
		this.errorLevel = errorLevel;
		this.message = message;
	}

	public FileConvertorException(final int code, final ErrorLevel errorLevel, final String message) {
		super(message);
		this.code = code;
		this.errorLevel = errorLevel;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public ErrorLevel getErrorLevel() {
		return errorLevel;
	}

	public String getMessage() {
		return message;
	}

	
	
}
