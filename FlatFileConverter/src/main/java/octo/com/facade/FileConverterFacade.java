package octo.com.facade;

import client.FlatFileConverter;
import octo.com.exception.InvalidFileFormatException;
import octo.com.exception.InvalidNumberFormatException;
import octo.com.service.FileConverterService;
import octo.com.utils.Metadata;
import org.apache.log4j.Category;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileConverterFacade {

    String inputFilePath;
    String metadataFilePath;
    String outputFilePath;
    final static Logger LOG = Logger.getLogger(FileConverterFacade.class);
    final String SUCCESS_STATUS = "File Converted Successfully.";

    public FileConverterFacade(String metadataFilePath, String inputFilePath, String outputFilePath){

        this.inputFilePath = inputFilePath;
        this.metadataFilePath = metadataFilePath;
        this.outputFilePath = outputFilePath;
    }

    public String convertFileUsingMetadata() throws FileNotFoundException, InvalidFileFormatException, InvalidNumberFormatException, IOException{

        FileConverterService fileConverterService =  new FileConverterService();

        // Throw an exception if the file does not exist.
        /*boolean inputFileExists = validateIfFileExists(inputFilePath);
        boolean metadataFileExists = validateIfFileExists(metadataFilePath);*/
        if(validateIfFileExists(inputFilePath) && validateIfFileExists(metadataFilePath)) {
            // Create Metadata Object if the input file and metadata file exists on the specified path
            Metadata metaData = new Metadata(metadataFilePath);

            // Read the input file and convert as per the metadata.
            try (BufferedReader input = Files.newBufferedReader(Paths.get(inputFilePath), StandardCharsets.UTF_8)) {
                BufferedWriter output = Files.newBufferedWriter(Paths.get(outputFilePath), StandardCharsets.UTF_8);
                output.write(metaData.convertToCSV());
                String nextLine;
                while ((nextLine = input.readLine()) != null) {
                /* Can be switched on to make sure Input Line is equal to sum of all the columns mentioned in metadata.
                This is currently being handled in Formatter code. */
                    //  matchInputDataWithMetadata(metaData,nextLine);
                    output.newLine();
                    output.write(fileConverterService.convertFile(nextLine, metaData));
                }

                output.close();
                input.close();
                return "File Converted Successfully.";
            } catch (IOException exception) {
                LOG.error("error occured during file conversion" + exception);
                throw new IOException("IO Exception");
            } catch (InvalidNumberFormatException e) {
                LOG.error("Number Format Exception" + e);
                throw new InvalidNumberFormatException("Number Format Exception");
            } catch (InvalidFileFormatException e) {
                LOG.error("Invalid file format exception" + e);
                throw new InvalidFileFormatException("Invalid File Format Exception");
            }
            catch (Exception e){
                throw e;
            }
        }else{
            throw new FileNotFoundException("One or both of required files not found");
        }
    }

    public boolean validateIfFileExists(String inputPath)
    {
        Path filePath = Paths.get(inputPath);
        return Files.exists(filePath);
    }

    /* Can be switched on to make sure Input Line is equal to sum of all the columns mentioned in metadata.
     This is currently being handled in Formatter code. */
    public boolean matchInputDataWithMetadata(Metadata metaData,String inputLine) throws InvalidFileFormatException {

        if (inputLine.length() != metaData.getTotalFieldsSize()) {
            throw new InvalidFileFormatException("Metadata Format Does Not Match with Input Data");
        }
        return true;
    }


}
