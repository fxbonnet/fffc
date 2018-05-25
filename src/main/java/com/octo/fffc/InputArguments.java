package com.octo.fffc;


/**
 * Container class for holding the input arguments
 *
 * @author anoop.shiralige
 * @version 1.0.0
 * @since 1.0.0
 */
public class InputArguments {
    private final String inputFile;
    private final String outputFile;
    private final String configFile;

    public InputArguments(String inputFile, String configFile, String outputFile) {
        this.inputFile = inputFile;
        this.configFile = configFile;
        this.outputFile = outputFile;
    }

    public String getInputFile() {
        return inputFile;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public String getConfigFile() {
        return configFile;
    }

    @Override
    public String toString() {
        return "{ inputFile='" + inputFile + '\'' +
                ", outputFile='" + outputFile + '\'' +
                ", configFile='" + configFile + '\'' +
                '}';
    }
}
