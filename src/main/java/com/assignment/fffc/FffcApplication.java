package com.assignment.fffc;

import com.assignment.fffc.constants.Constants;
import com.assignment.fffc.services.FormatConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class FffcApplication {


    public static void main(String[] args) {
        SpringApplication.run(FffcApplication.class, args);
    }


    public static int writerBufferSize;

    @Value("${writer.buffer.size}")
    public void setDatabase(int bufferSize) {
        writerBufferSize = bufferSize;
    }
}

