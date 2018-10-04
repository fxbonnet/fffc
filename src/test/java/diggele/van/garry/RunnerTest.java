package diggele.van.garry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Class only tests the running of the program and ensures files are output under correct circumstances.
 * It is not necessary to test parsers/transformers as they have already been covered under junit test scope.
 */
public class RunnerTest {

    private Runner runner;
    private String expected = "Birth date,First name,Last name,Weight\r\n" +
            "01/01/1970,John,Smith,81.5\r\n" +
            "31/01/1975,Jane,Doe,61.1\r\n" +
            "28/11/1988,\"Bo'b\",Big,102.4\r\n";

    @Before
    public void setUp() {
        runner = new Runner();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void run() throws IOException {
        String[] args = {
                "--classpathLoad",
                "--metadataFile",
                "test.metadata",
                "--inputFile",
                "test.data",
                "--outputFile",
                "results.csv"
        };
        try {
            Files.delete(Paths.get("results.csv"));
        } catch (IOException aE) {
            // Nothing to delete
        }
        runner.run(args);
        String actual = convertToString(Files.readAllLines(Paths.get("results.csv")));
        assertEquals(expected, actual);
        Files.delete(Paths.get("results.csv"));
    }


    @Test(expected = RuntimeException.class)
    public void run_bad1() {
        String[] args = {
                "--classpathLoad",
                "--inputFile",
                "test.data",
                "--outputFile",
                "results.csv"
        };
        runner.run(args);
    }

    @Test(expected = RuntimeException.class)
    public void run_bad2() {
        String[] args = {
                "--classpathLoad",
                "--metadataFile",
                "test.metadata",
                "--outputFile",
                "results.csv"
        };
        runner.run(args);
    }

    @Test(expected = RuntimeException.class)
    public void run_bad3() {
        String[] args = {
                "--metadataFile",
                "test.metadata",
                "--inputFile",
                "test.data",
                "--outputFile"
        };
        runner.run(args);
    }


    private String convertToString(final List<String> aReadAllLines) {
        StringBuilder stringBuilder = new StringBuilder();
        for (final String results : aReadAllLines) {
            stringBuilder.append(results);
            stringBuilder.append("\r\n");
        }
        return stringBuilder.toString();
    }
}