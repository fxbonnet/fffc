package com.assignment.fffc;

import com.assignment.fffc.formats.ColumnFormatProvider;
import com.assignment.fffc.processors.CSVDataProcessor;
import com.assignment.fffc.processors.DataProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FffcApplication {

	public static void main(String[] args) {
		SpringApplication.run(FffcApplication.class, args);
	}

}

