package com.elo8.fffc;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
public class FffToCsvLineConverter {

    private Map<String, Function<String, String>> typeFormatter = new HashMap<String, Function<String, String>>() {
        {
            put(Metadata.DataType.date.name(), s -> {
                String retVal = "";
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate dateTime = LocalDate.parse(s, formatter);
                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                retVal = dateTime.format(outputFormatter);
                return retVal;
            });
            put(Metadata.DataType.string.name(), s -> {
                if (StringUtils.contains(s, ',')){
                    return StringUtils.trim(StringUtils.prependIfMissing(StringUtils.appendIfMissing(s, "\""), "\""));
                }
                return StringUtils.trim(s);
            });
            put(Metadata.DataType.numeric.name(), s -> {
                //validation
                return Float.valueOf(s.trim()).toString();
            });
        }
    };

    private String format(String metadataType, String text) {
        return typeFormatter.get(Metadata.DataType.valueOf(metadataType).name()).apply(text);
    }


    public String toCsvRow(String inputLine, List<Metadata> metadataList) {

        if (!isValidDataRow(inputLine, metadataList)) {
            // should consider to create a custom exception type but for brevity am just using Exception
            throw new IllegalArgumentException("Length of data row does not match metadata specification");
        }
        StringBuilder sb = new StringBuilder();
        int currOffset = 0;
        for (Metadata metadata: metadataList) {
            int endIndex = currOffset + Integer.valueOf(metadata.getLength());
            String dataField = inputLine.substring(currOffset, endIndex).trim();
            sb.append(format(metadata.getType(), dataField));
            currOffset = currOffset + Integer.valueOf(metadata.getLength());
            if (metadataList.indexOf(metadata) < metadataList.size()-1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    // simple validation logic to prevent processing further if specified line length in the metadata
    // does not match the input length
    private boolean isValidDataRow(String inputLine, List<Metadata> metadataList) {
        final int[] totalLengthAsPerMetadata = {0};
        metadataList.forEach( metadata -> totalLengthAsPerMetadata[0] += metadata.getLength());
        return ( totalLengthAsPerMetadata[0] == StringUtils.trim(inputLine).length());
    }

}
