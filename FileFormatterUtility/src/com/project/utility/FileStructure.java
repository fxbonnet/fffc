package com.project.utility;

/**
 * Meta data file structure.
 * Created by rssridh on 12/3/17.
 */
public class FileStructure {
    // Can also just use a getter and setter annotation.
    private String name;
    private int length;
    private String type;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
