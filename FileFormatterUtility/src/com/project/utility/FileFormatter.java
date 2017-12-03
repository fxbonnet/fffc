package com.project.utility;

import java.util.ArrayList;
import java.util.Scanner;
import com.project.utility.exception.FileFormatterException;
import com.project.utility.exception.IncorrectDataFormatException;


/**
 * The main class for the file formatter utility.
 * Created by rssridh on 12/3/17.
 */
public class FileFormatter {

    public static void main(String[] args) throws FileFormatterException, IncorrectDataFormatException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the Metadata file name");
        String metadataFile = scanner.next();
        System.out.println(metadataFile);

        System.out.println("Enter the Input file name");
        String inputFile = scanner.next();
        System.out.println(inputFile);

        System.out.println("Enter the output file name");
        String outputFile = scanner.next();
        System.out.println(outputFile);

        FileConverter fileConverter = new FileConverter(inputFile, outputFile, metadataFile);
        fileConverter.convert();

        System.out.println("File conversion completed");

    }

}
