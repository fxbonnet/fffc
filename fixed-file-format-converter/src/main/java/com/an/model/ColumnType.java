package com.an.model;

import com.an.util.DateFormatter;

import java.text.SimpleDateFormat;
import java.util.Arrays;

public enum ColumnType {

    DATE("date"),
    NUMERIC("numeric"),
    STRING("string"),
    UNKNOWWN("unknown");


    private String code;

    private ColumnType(String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }

    public static ColumnType forValue(String code) {
        return Arrays.stream(ColumnType.values()).filter(r -> r.getCode().equalsIgnoreCase(code) ).findFirst().orElse(UNKNOWWN);
    }


}
