package com.octo.converter.service.util;

import java.io.File;
import java.util.Optional;

public class FileUtils {

    public static String getExtension(String filename) {
       Optional<String> extension=Optional.ofNullable(filename)
                .filter(f -> f.contains(Constants.dot))
                .map(f -> f.substring(filename.lastIndexOf(Constants.dot) + 1));

       if(extension.isPresent()){
           return extension.get();
       }else {
           return "";
       }
    }

    public static Boolean isEmpty(File file){
        if(file.length()==0) return true;
        return false;
    }

}
