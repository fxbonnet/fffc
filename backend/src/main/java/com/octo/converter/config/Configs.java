package com.octo.converter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class Configs {
    private Environment env;
    public String FFF_DATE_FORMAT=null;
    public String CSV_DATE_FORMAT=null;
    public String TMP_FOLDER=null;
    public  Boolean IS_SPACE_ALLOWED_INSIDE_QUOTES=null;
    public String RESULT_FILE_NAME="result.csv";
    public String CSV_ROW_SEPARATOR=null;
    public String CSV_COLUMN_SEPARATOR=null;
    public char STRING_IDENTIFIER='\"';
    @Autowired
    Configs(Environment env){
        this.env=env;
       FFF_DATE_FORMAT=env.getProperty("fff.date.format");
       CSV_DATE_FORMAT=env.getProperty("csv.date.format");
        TMP_FOLDER=env.getProperty("tmpfolder");
        IS_SPACE_ALLOWED_INSIDE_QUOTES=Boolean.valueOf(env.getProperty("csv.allowSpaceInsideString"));
        RESULT_FILE_NAME=env.getProperty("resultfile");
        CSV_COLUMN_SEPARATOR=env.getProperty("csv.column.separator");
        CSV_ROW_SEPARATOR=env.getProperty("csv.row.separator");
        STRING_IDENTIFIER=env.getProperty("fff.string.identifier").charAt(0);
    }
}
