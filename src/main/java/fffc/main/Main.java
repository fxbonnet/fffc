package fffc.main;

import fffc.Exceptions.InvalidFieldFormatException;
import fffc.Exceptions.InvalidLineFormatException;
import fffc.entities.MetaData;
import fffc.service.Transformer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Main class. Top level process happens here
 *
 */
public class Main
{
    public static void main(String[] args ) {

        int lineNumber = 1;

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(args[1]), StandardCharsets.UTF_8) ){

            MetaData metaData = new MetaData(args[0]);

            BufferedWriter output = Files.newBufferedWriter(Paths.get(args[2]), StandardCharsets.UTF_8);

            output.write(metaData.asCsv());

            String line;
            while ((line = reader.readLine()) != null) {
                output.newLine();
                output.write(Transformer.transform(line, metaData));
                lineNumber ++;
            }

            output.close();
            reader.close();

        } catch (IOException io) {
            System.out.println("Error happened during disk IO:"+io.getMessage());
        } catch (InvalidLineFormatException e) {
            System.out.println("Error! Invalid line format at line: "+lineNumber+" - "+e.getMessage());
        } catch (InvalidFieldFormatException e) {
            System.out.println("Error! Invalid field format at line: "+lineNumber+" - "+e.getMessage());
        }
    }
}
