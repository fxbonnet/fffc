package com.octo.fffc.model.transformer;

import com.octo.fffc.exceptions.TransformationException;
import com.octo.fffc.reader.FFFCReader;
import com.octo.fffc.reader.Reader;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TransformerTest {

    @Test
    public void testValidTransformer() throws IOException {
        FFFCTransformer transformer = new FFFCTransformer("src/test/resources/metadata.dat",
                "src/test/resources/input.dat",
                "src/test/resources/output.csv");

        transformer.transform();

        List<String> expected = getRecords("src/test/resources/expected.csv");
        List<String> output = getRecords("src/test/resources/output.csv");

        assertEquals(expected, output);
    }

    @Test(expected = TransformationException.class)
    public void testMetadataFileNotFound() {
        FFFCTransformer transformer = new FFFCTransformer("src/test/resources/metadata1.dat",
                "src/test/resources/input.dat",
                "src/test/resources/error.csv");

        transformer.transform();
    }

    @Test(expected = TransformationException.class)
    public void testInputFileNotFound() {
        FFFCTransformer transformer = new FFFCTransformer("src/test/resources/metadata.dat",
                "src/test/resources/input1.dat",
                "src/test/resources/error.csv");

        transformer.transform();
    }

    @Test(expected = TransformationException.class)
    public void testOutputFileNotFound() {
        FFFCTransformer transformer = new FFFCTransformer("src/test/resources/metadata.dat",
                "src/test/resources/input.dat",
                "/compass/output.csv");

        transformer.transform();
    }

    @Test
    public void testEmptyInputFile() {
        FFFCTransformer transformer = new FFFCTransformer("src/test/resources/metadata.dat",
                "src/test/resources/emptyinput.dat",
                "src/test/resources/emptyoutput.csv");

        transformer.transform();
    }

    private List<String> getRecords(String file) throws IOException {
        List<String> records = new ArrayList<>();
        try (Reader reader = new FFFCReader(file)) {
            String line;
            while((line = reader.nextLine()) != null) {
                records.add(line);
            }

        }

        return records;
    }
}