package com.assignment.fffc;

import com.assignment.fffc.constants.Constants;
import com.assignment.fffc.services.FormatConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class FffcApplication /*implements CommandLineRunner*/ {


   /* private static Logger LOG = LoggerFactory
            .getLogger(FffcApplication.class);

    private FormatConverter converter;

    @Autowired
    public FffcApplication(FormatConverter converter) {
        this.converter = converter;
    }*/

    public static void main(String[] args) {
        SpringApplication.run(FffcApplication.class, args);
    }

    /*@Override
    public void run(String... args) throws Exception {

        if(args.length > Constants.MINIMUM_NO_OF_ARGUMENTS){
            converter.convert(args[0], args[1], args[2], args[3]);
        }else{
            throw new IllegalArgumentException("");
        }

    }*/
}

