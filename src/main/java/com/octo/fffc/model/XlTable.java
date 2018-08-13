package com.octo.fffc.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author alanterriaga
 * @project FFFC
 */
public class XlTable {

    private List<MetadataColumn> headers = new ArrayList<>();

    private List<XlRow> rows = new ArrayList<>();

    public List<MetadataColumn> getHeaders() {
        return headers;
    }

    public void setHeaders(List<MetadataColumn> headers) {
        this.headers = headers;
    }

    public List<XlRow> getRows() {
        return rows;
    }

    public void setRows(List<XlRow> rows) {
        this.rows = rows;
    }
}
