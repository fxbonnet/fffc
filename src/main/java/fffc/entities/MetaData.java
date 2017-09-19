package fffc.entities;

import fffc.enums.Type;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  Loads and holds a whole meta data file in memory.
 */
public class MetaData {

    private List<FieldMetaData> metaData = new ArrayList<>();

    public MetaData(String file) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(file), StandardCharsets.UTF_8);

        for (String line: lines) {
            String[] fields = line.split(",");
            FieldMetaData fieldMetaData = new FieldMetaData();
            fieldMetaData.setTitle(fields[0]);
            fieldMetaData.setLength(Integer.valueOf(fields[1]));
            fieldMetaData.setType(Type.valueOf(fields[2].toUpperCase()));

            metaData.add(fieldMetaData);
        }
    }

    public List<FieldMetaData> getMetaData() {
        return metaData;
    }

    public String asCsv() {
         return metaData.stream().map(FieldMetaData::getTitle).collect( Collectors.joining( "," ) );
    }

    public int expectedLength(){
        int length = 0;

        for (FieldMetaData metaDatum : metaData) {
            length += metaDatum.length;
        }
        return length;
    }
}
