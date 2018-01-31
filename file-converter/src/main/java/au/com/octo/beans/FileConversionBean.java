package au.com.octo.beans;

public class FileConversionBean {

	private String dataFixedFormatFile;
	
	private String metadataFile;
	
	private String outputCSVFormatFile;

	public FileConversionBean (String dataFixedFormatFile, String metdataFile, String outputCSVFormatFile) {
		this.dataFixedFormatFile = dataFixedFormatFile;
		this.metadataFile = metdataFile;
		this.outputCSVFormatFile = outputCSVFormatFile;
	}
	
	
	/**
	 * @return the dataFixedFormatFile
	 */
	public String getDataFixedFormatFile() {
		return dataFixedFormatFile;
	}

	/**
	 * @param dataFixedFormatFile the dataFixedFormatFile to set
	 */
	public void setDataFixedFormatFile(String dataFixedFormatFile) {
		this.dataFixedFormatFile = dataFixedFormatFile;
	}

	/**
	 * @return the metdataFile
	 */
	public String getMetadataFile() {
		return metadataFile;
	}

	/**
	 * @param metdataFile the metdataFile to set
	 */
	public void setMetadataFile(String metdataFile) {
		this.metadataFile = metdataFile;
	}

	/**
	 * @return the outputFile
	 */
	public String getOutputCSVFormatFile() {
		return outputCSVFormatFile;
	}

	/**
	 * @param outputFile the outputFile to set
	 */
	public void setOutputCSVFormatFile(String outputFile) {
		this.outputCSVFormatFile = outputFile;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FileConversionBean [dataFixedFormatFile=" + dataFixedFormatFile + ", metdataFile=" + metadataFile
				+ ", outputFile=" + outputCSVFormatFile + "]";
	}
	
	
}
