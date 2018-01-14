package com.truform.fffc.datatypes;

public class CSVString {
    private String string;

    public CSVString(String input) {
        if (input.contains(",")) {
            this.string = "\"" + input.replaceFirst("\\s++$", "") + "\"";
        } else {
            this.string = input.replaceFirst("\\s++$", "");
        }
    }

    @Override
    public String toString() {
        return string;
    }
}
