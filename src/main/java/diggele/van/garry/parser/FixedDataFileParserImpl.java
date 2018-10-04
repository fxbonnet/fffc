package diggele.van.garry.parser;

import com.google.common.base.Preconditions;
import diggele.van.garry.model.Column;
import diggele.van.garry.model.GenericFileRepresentation;
import diggele.van.garry.model.GenericFileRow;
import diggele.van.garry.model.MetaDataFileDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public class FixedDataFileParserImpl implements Parser<GenericFileRepresentation> {
    final private Logger logger = LoggerFactory.getLogger(FixedDataFileParserImpl.class);

    private MetaDataFileDefinition metaDataFileDefinition;

    private GenericFileRepresentation genericFileRepresentation;

    private static boolean isValidUTF8(final byte[] bytes) {
        try {
            Charset.availableCharsets().get("UTF-8").newDecoder().decode(ByteBuffer.wrap(bytes));
        } catch (CharacterCodingException e) {
            return false;
        }
        return true;
    }

    @Override
    public GenericFileRepresentation parse(final Path aFile) throws IOException {
        Preconditions.checkNotNull(metaDataFileDefinition);
        genericFileRepresentation = new GenericFileRepresentation(metaDataFileDefinition);
        int lineNumber = 0;
        for (String s : Files.readAllLines(aFile)) {
            lineNumber++;
            if (!isValidUTF8(s.getBytes())) {
                throw new RuntimeException("File is not encoded in UTF-8 format.");
            }
            accept(s, lineNumber);
        }

        return genericFileRepresentation;
    }

    private void accept(String line, int aLineNumber) {
        logger.debug("Parameters passed to the class {}, method {}: {}, {}"
                , this.getClass().getSimpleName()
                , "accept"
                , "line"
                , aLineNumber);
        genericFileRepresentation.addRow(extractRow(line));
    }

    private GenericFileRow extractRow(final String aLine) {
        GenericFileRow genericFileRow = new GenericFileRow();
        int currentFieldPosition = 0;
        int endIndexPosition = 0;

        // we use position 1 for start element as that is what humans are accustomed to for data file lookups.
        for (int position = 1; position <= getNumberOfExpectedColumns(); position++) {
            Column tmpColumn;
            Column cloneColumn = metaDataFileDefinition.getColumnAtPosition(position);
            if (cloneColumn == null) {
                throw new RuntimeException("No column defined for position " + position);
            }
            tmpColumn = new Column.ColumnBuilder().createColumnClone(cloneColumn);
            endIndexPosition += tmpColumn.getLength();
            if (endIndexPosition > aLine.length()) endIndexPosition = aLine.length() - 1;

            tmpColumn.setValue(aLine.substring(currentFieldPosition, endIndexPosition).trim());
            genericFileRow.addColumn(tmpColumn);

            currentFieldPosition = endIndexPosition;
        }
        return genericFileRow;
    }

    public void setMetaDataFileDefinition(MetaDataFileDefinition aMetaDataFileDefinition) {
        metaDataFileDefinition = aMetaDataFileDefinition;
    }

    private int getNumberOfExpectedColumns() {
        return metaDataFileDefinition.getNumberOfColumns();
    }
}
