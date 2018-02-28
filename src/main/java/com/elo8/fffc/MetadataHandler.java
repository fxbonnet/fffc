package com.elo8.fffc;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Component
public class MetadataHandler {

    public List<Metadata> collectMetadata(Path metadataFile) throws IOException {
        List<Metadata> metadataList = new ArrayList<>();
        try (Stream<String> stream = Files.lines(metadataFile)) {
            stream.forEach(item -> metadataList.add(toMetadata(item)));
        } catch (IOException ioex) {
            System.out.println("Error in processing the file: " + ioex.getMessage());
            throw ioex;
        } catch (Exception ex) {
            System.out.println("Unexpected runtime exceptions: " + ex.getMessage());
            throw ex;
        }
        return metadataList;
    }

    private Metadata toMetadata(String row) {
        String[] tokens = row.split(",");
        Metadata item = new Metadata(tokens[0], tokens[1], tokens[2]);
        return item;
    }

}
