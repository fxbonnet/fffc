package com.elo8.fffc;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellComponent;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

@ShellComponent
public class CLICommands {
    @Autowired
    private MetadataHandler metadataHandler;
    @Autowired
    private FffToCsvLineConverter fffToCsvLineConverter;
    private static String LINE_SEPARATOR = "\r\n";

    @ShellMethod("Converting fixed file format to CSV based on metadata file")
    public void fff2csv(String metadataURI, String dataURI, String outURI) {
        Path metadataFilePath = Paths.get(URI.create(metadataURI));
        try {
            List<Metadata> metadataList = metadataHandler.collectMetadata(metadataFilePath);
            File csvFile = new File(URI.create(outURI));
            FileUtils.deleteQuietly(csvFile);
            try (Stream<String> stream = Files.lines(Paths.get(URI.create(dataURI)))) {
                stream.forEach(item -> {
                    try {
                        FileUtils.write(csvFile, fffToCsvLineConverter.toCsvRow(item, metadataList) + LINE_SEPARATOR, "UTF-8", true);
                    } catch (IOException ex) {
                        System.out.println("Exceptions when writing data to file, details: " + ex.getMessage());
                    }
                });
            } catch (IOException ex) {
                System.out.println("Exceptions reading file, details: " + ex.getMessage());
            }
        } catch (IOException ex) {
            System.out.println("Exceptions while processing file(s), details: " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Failed converting fixed file format to csv, details: " + ex.getMessage());
        }
        System.out.println("Successfully converted fixed file format to csv");
        System.out.println("Metadata file: " + metadataURI);
        System.out.println("Fixed format file: " + dataURI);
        System.out.println("Output CSV file: " + outURI);
    }
}