package com.octo.fffc.model;

/**
 * @author alanterriaga
 * @project FFFC
 */
public class InputRequestDto {

    private String dataFileName;
    private String metaDataFileName;

    public String getDataFileName() {
        return dataFileName;
    }

    public void setDataFileName(String dataFileName) {
        this.dataFileName = dataFileName;
    }

    public String getMetaDataFileName() {
        return metaDataFileName;
    }

    public void setMetaDataFileName(String metaDataFileName) {
        this.metaDataFileName = metaDataFileName;
    }
}
