package com.octo.coverter;

/**
 * Base interface for all the file converters.
 * This interface defines the the lifecycle methods requires to be implemented by file converters.
 * Abstract file converters should implement these hooks any pre and post execution can be handled.
 */
public interface FileConverter {

    /**
     * this can be called to initialize the converter
     */
    void init();

    /**
     * Perform the file conversion
     *
     * @return
     */
    boolean convert();

    /**
     * clean up hook
     */
    void cleanUp();

}
