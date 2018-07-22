package com.octo.au;

import static java.lang.System.exit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.octo.au.constants.Constants;
import com.octo.au.domain.service.FileFormatConverterService;

@SpringBootApplication
public class SpringBootConsoleApplication implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(SpringBootConsoleApplication.class);
    @Autowired
    private FileFormatConverterService fffcService;

    public static void main(String[] args) throws Exception {

        //disabled banner, don't want to see the spring logo
        SpringApplication app = new SpringApplication(SpringBootConsoleApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);

        //SpringApplication.run(SpringBootConsoleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info(Constants.STR_CUSTOM_COMMENT_IDENTIFIER+"File Writing Initiated");
        fffcService.writeCsvFile("metatdata.txt","data.txt");
        logger.info(Constants.STR_CUSTOM_COMMENT_IDENTIFIER+"File Writing Successful");
        exit(0);
    }
}