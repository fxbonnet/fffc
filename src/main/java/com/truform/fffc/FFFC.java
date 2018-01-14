package com.truform.fffc;

import com.truform.fffc.datatypes.Metadata;
import com.truform.fffc.helpers.FixedFileConverter;
import com.truform.fffc.helpers.MetadataReader;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FFFC {
    public static void main(String[] args) {
        if (args.length != 3) {
            throw new RuntimeException("Argument missing, expected <data file> <metadata file> <output file>");
        }

        Path dataFile = Paths.get(args[0]);
        Path metadataFile = Paths.get(args[1]);
        Path outputFile = Paths.get(args[2]);

        Metadata metadata = MetadataReader.readMetadata(metadataFile);

        try (Stream<String> stream = Files.lines(dataFile)) {

            // A new try-with-resources means exceptions from data and output files can be differentiated
            try (BufferedWriter writer = Files.newBufferedWriter(outputFile)) {

                writer.write(FixedFileConverter.getHeader(metadata) + "\n");

                stream.forEach((String line) -> {
                    String newLine = FixedFileConverter.convertLine(line, metadata) + "\n";

                    try {
                        writer.write(newLine);
                    } catch (IOException e) {
                        throw new RuntimeException("Output file couldn't be written to", e);
                    }
                });
            } catch (IOException e) {
                throw new RuntimeException("Output file couldn't be accessed", e);
            }
        } catch (IOException e) {
            throw new RuntimeException("Data file couldn't be read", e);
        }
    }
}
