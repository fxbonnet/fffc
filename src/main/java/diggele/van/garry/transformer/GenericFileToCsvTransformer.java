package diggele.van.garry.transformer;

import diggele.van.garry.model.Column;
import diggele.van.garry.model.GenericFileRepresentation;
import diggele.van.garry.model.MetaDataFileDefinition;

public class GenericFileToCsvTransformer implements GenericFileTransformer {

    private MetaDataFileDefinition metaDataFileDefinition;

    private String separator = ",";
    private String eolSeparator = "\r\n";

    @Override
    public String transform(final GenericFileRepresentation aGenericFileRepresentation) {
        metaDataFileDefinition = aGenericFileRepresentation.getMetaDataFileDefinition();
        return convertRows(aGenericFileRepresentation, getHeaderLine());
    }

    private String convertRows(final GenericFileRepresentation aGenericFileRepresentation, final String aHeaderLine) {
        StringBuilder stringBuilder = new StringBuilder(aHeaderLine);
        aGenericFileRepresentation.getFileRows().forEach(aGenericFileRow -> {
            // as before for human understanding we are making position 1 the first column.
            for (int position = 1; position <= aGenericFileRow.getNumberOfColumns(); position++) {
                Column tmpColumn = aGenericFileRow.getColumnByPosition(position);
                if (tmpColumn != null) {
                    stringBuilder.append(tmpColumn.getValue());
                }

                if (position != aGenericFileRow.getNumberOfColumns()) {
                    stringBuilder.append(separator);
                }
            }
            stringBuilder.append(eolSeparator);
        });
        return stringBuilder.toString();
    }

    private String getHeaderLine() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int position = 1; position <= metaDataFileDefinition.getNumberOfColumns(); position++) {
            Column tmpColumn = metaDataFileDefinition.getColumnAtPosition(position);
            if (tmpColumn == null) {
                throw new RuntimeException("Column definition missing for index position " + position);
            }
            stringBuilder.append(tmpColumn.getName());
            stringBuilder.append(separator);
        }
        return cleanLastColumnAddEOL(stringBuilder.toString());
    }

    private String cleanLastColumnAddEOL(String aLine) {
        // dodgy but trim the last separator, faster than checking each time if last element.
        String tmpString = aLine.substring(0, aLine.length() - 1);
        return tmpString + eolSeparator;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(final String aSeparator) {
        separator = aSeparator;
    }

    public String getEolSeparator() {
        return eolSeparator;
    }

    public void setEolSeparator(final String aEolSeparator) {
        eolSeparator = aEolSeparator;
    }
}
