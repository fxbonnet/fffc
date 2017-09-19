package fffc.main;


import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * Test for the main method
 */
public class MainTest
{

    @Test
    public void testGeneratingOutput() throws IOException {

        Main.main(new String[] { "src/test/resources/metadata.txt", "src/test/resources/data.txt", "src/test/resources/testoutput.txt" });

        Assert.assertTrue(FileUtils.contentEquals(new File( "src/test/resources/output.txt"), new File( "src/test/resources/testoutput.txt")));

        new File("src/test/resources/testoutput.txt").delete(); // comment this to look into the generated file
    }
}
