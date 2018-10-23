package com.octotechnology.fffc;

public class ConfigData {

	String inputFilePath;
	String metaDataFilePath;
	String outPutFilePath;

	public ConfigData(String inputFilePath, String metaDataFilePath, String outPutFilePath) {
		this.inputFilePath = inputFilePath;
		this.metaDataFilePath = metaDataFilePath;
		this.outPutFilePath = outPutFilePath;
	}

	public String getInputFilePath() {
		return inputFilePath;
	}

	public String getMetaDataFilePath() {
		return metaDataFilePath;
	}

	public String getOutPutFilePath() {
		return outPutFilePath;
	}
}
