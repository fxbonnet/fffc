package io.file.converter;

import io.file.converter.exception.DataFormatException;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;

public class TestConverterExecutor {

    /**
     * Test if metadata file get the right format
     * {Title,size,type}
     */
    @Test
    public void SuccessMetaDataFormatTest() throws URISyntaxException {

        String mainPath = Paths.get(ClassLoader.getSystemResource("headerDescriptor.csv").toURI()).toString();

        ConverterExecutor converterExecutor = new ConverterExecutor(mainPath,
                null,
                null);

    }

    /**
     * Test if metadata file get a bad format
     * {Title,size} instead of {Title,size,type}
     */
    @Test(expected = IllegalArgumentException.class)
    public void ErrorMetaDataFormatTest() throws URISyntaxException {

        URI uri = ClassLoader.getSystemResource("test-data-error/headerDescriptorWithBadFormat.csv").toURI();
        String mainPath = Paths.get(uri).toString();

        try {
            ConverterExecutor converterExecutor = new ConverterExecutor(mainPath,
                    null,
                    null);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }

    }

    /**
     * Test if we get a bad date format from the source
     */
    @Test(expected = DataFormatException.class)
    public void ErrorConverterNumericFormatTest() throws URISyntaxException {

        String pathMetaData = Paths.get(ClassLoader.getSystemResource("headerDescriptor.csv").toURI()).toString();

        String pathDataSource = Paths.get(ClassLoader.getSystemResource("test-data-error/dataWithErrorNumericFormat.txt").toURI()).toString();

        String pathDataTarget = Paths.get(ClassLoader.getSystemResource("test-data-error/result.csv").toURI()).toString();

        ConverterExecutor converterExecutor = new ConverterExecutor(pathMetaData,
                pathDataSource,
                pathDataTarget);

        try {
            converterExecutor.convert();
        } catch (Exception e ) {
            System.out.println(e.getMessage());
            assert e.getMessage().contains("Validation error, this value should be a numeric");
            throw e;
        }
    }

    /**
     * Test if we get a bad numeric format from the source
     */
    @Test(expected = DataFormatException.class)
    public void ErrorConverterDateFormatTest() throws URISyntaxException {

        String pathMetaData = Paths.get(ClassLoader.getSystemResource("headerDescriptor.csv").toURI()).toString();

        String pathDataSource = Paths.get(ClassLoader.getSystemResource("test-data-error/dataWithErrorDateFormat.txt").toURI()).toString();

        String pathDataTarget = Paths.get(ClassLoader.getSystemResource("test-data-error/result.csv").toURI()).toString();

        ConverterExecutor converterExecutor = new ConverterExecutor(pathMetaData,
                pathDataSource,
                pathDataTarget);

        try {
            converterExecutor.convert();
        } catch (Exception e ) {
            System.out.println(e.getMessage());
            assert e.getMessage().contains("Validation error, this value should be a date");
            throw e;
        }
    }
}
