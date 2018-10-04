package diggele.van.garry;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import diggele.van.garry.model.GenericFileRepresentation;
import diggele.van.garry.model.MetaDataFileDefinition;
import diggele.van.garry.parser.FixedDataFileParserImpl;
import diggele.van.garry.parser.MetaDataFileParserImpl;
import diggele.van.garry.parser.Parser;
import diggele.van.garry.transformer.GenericFileToCsvTransformer;
import diggele.van.garry.transformer.GenericFileTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Runner {
    final private Logger logger = LoggerFactory.getLogger(MetaDataFileParserImpl.class);

    @Parameter(names = {"--metadataFile", "-m"}, required = true, description = "The meta data file which described the structure of the file to be converted")
    String metaDataFile;
    @Parameter(names = {"--inputFile", "-i"}, required = true, description = "The file to convert from given input into a CSV file. The input file can be relative and absolute path.")
    String inputFile;
    @Parameter(names = {"--outputFile", "-o"}, required = true, description = "The output CSV file. The output file can be relative and absolute path.")
    String outputFile;
    @Parameter(names = {"--classpathLoad", "-c"}, description = "Where to load the files to parse from.")
    boolean loadFromClasspath = false;

    @Parameter(names = "--help", help = true)
    private boolean help;

    public static void main(String[] aArgs) {
        System.exit(new Runner().run(aArgs));
    }

    protected int run(final String[] aArgs) {
        int initialisedCode = initialise(aArgs);
        if (initialisedCode != 0) return initialisedCode;
        if (help) return initialisedCode;

        Parser<MetaDataFileDefinition> metaDataFileParser = new MetaDataFileParserImpl();
        FixedDataFileParserImpl fixedDataFileParser = new FixedDataFileParserImpl();
        GenericFileTransformer csvTransformer = new GenericFileToCsvTransformer();

        try {
            MetaDataFileDefinition metaDataFileDefinition = metaDataFileParser.parse(loadInputDataPath(metaDataFile));
            fixedDataFileParser.setMetaDataFileDefinition(metaDataFileDefinition);
            GenericFileRepresentation genericFileRepresentation = fixedDataFileParser.parse(loadInputDataPath(inputFile));

            String tmp = csvTransformer.transform(genericFileRepresentation);
            Files.write(Paths.get(outputFile), tmp.getBytes(), StandardOpenOption.CREATE_NEW);
        } catch (IOException | URISyntaxException aE) {
            aE.printStackTrace();
        }

        return 0;
    }

    private int initialise(final String[] aArgs) {
        JCommander jCommander = null;
        try {
            jCommander = JCommander
                    .newBuilder()
                    .programName("Octo Fixed file format converter")
                    .addObject(this)
                    .build();
            jCommander.parse(aArgs);
            if (help) {
                jCommander.usage();
                return 0;
            }
        } catch (ParameterException pe) {
            logger.error("Error validating your parameters." + pe);
            if (jCommander != null) jCommander.usage();
            throw new RuntimeException(pe);
        }
        return 0;
    }

    private Path loadInputDataPath(String fileName) throws URISyntaxException {
        return (loadFromClasspath) ? Paths.get(ClassLoader.getSystemResource(fileName).toURI()) : Paths.get(fileName);
    }
}
