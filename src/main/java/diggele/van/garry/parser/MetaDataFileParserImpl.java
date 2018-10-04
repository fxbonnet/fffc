package diggele.van.garry.parser;

import diggele.van.garry.model.Column;
import diggele.van.garry.model.MetaDataFileDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MetaDataFileParserImpl implements Parser<MetaDataFileDefinition> {
    final private Logger logger = LoggerFactory.getLogger(MetaDataFileParserImpl.class);

    private MetaDataFileDefinition metaDataFileDefinition = new MetaDataFileDefinition();
    private String metaSeparator = ",";
    private int numberOfExpectedColumns = 3;

    @Override
    public MetaDataFileDefinition parse(final Path aFile) throws IOException {
        int lineNumber = 0;
        for (String s : Files.readAllLines(aFile)) {
            lineNumber++;
            accept(s, lineNumber);
        }
        return metaDataFileDefinition;
    }

    private void accept(String line, int aLineNumber) {
        logger.debug("Parameters passed to the class {}, method {}: {}, {}"
                , this.getClass().getSimpleName()
                , "accept"
                , "line"
                , aLineNumber);
        try {
            metaDataFileDefinition.addColumnDefinition(extractColumn(line, aLineNumber));
        } catch (InstantiationException | IllegalAccessException aE) {
            throw new RuntimeException("Unable to add column definition. Please verify the metadata file." + aE);
        }

    }

    private Column extractColumn(final String aLineToParse, int aPosition) throws InstantiationException, IllegalAccessException {
        String[] splitResult = aLineToParse.split(metaSeparator);
        if (splitResult.length != numberOfExpectedColumns) {
            logger.error("Unable to parse meta data file with the following contents: {}", aLineToParse);
            throw new RuntimeException("Unexpected number of columns whilst parsing the metadata file");
        }
        return new Column.ColumnBuilder()
                .position(aPosition)
                .name(splitResult[0])
                .length(Integer.parseInt(splitResult[1].trim()))
                .type(splitResult[2])
                .createColumn();
    }

    public String getMetaSeparator() {
        return metaSeparator;
    }

    public void setMetaSeparator(final String aMetaSeparator) {
        metaSeparator = aMetaSeparator;
    }

    public int getNumberOfExpectedColumns() {
        return numberOfExpectedColumns;
    }

    public void setNumberOfExpectedColumns(final int aNumberOfExpectedColumns) {
        numberOfExpectedColumns = aNumberOfExpectedColumns;
    }
}
