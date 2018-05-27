package octo.exception;

/**
 * Created by anjana on 27/05/18.
 */
public class MetadataFileException extends Exception {

    public MetadataFileException(String message) {
        super(message);
    }

    public MetadataFileException(String message, Throwable t) {
        super(message, t);
    }
}
