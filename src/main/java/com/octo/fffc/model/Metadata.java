package com.octo.fffc.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author alanterriaga
 * @project FFFC
 */
public class Metadata {

    private List<MetadataColumn> metadataColumnList = new ArrayList<>();

    public List<MetadataColumn> getMetadataColumnList() {
        return metadataColumnList;
    }

    public void setMetadataColumnList(List<MetadataColumn> metadataColumnList) {
        this.metadataColumnList = metadataColumnList;
    }
}
