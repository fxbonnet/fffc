package com.octo.fffc.formatter;


/**
 * @author anoop.shiralige
 * @version 1.0.0
 * @since 1.0.0
 */
public class InitialConfig {
    private final String inputFile;
    private final String outputFile;
    private final String configFile;

    public InitialConfig(String inputFile, String outputFile, String configFile) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.configFile = configFile;
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
}
