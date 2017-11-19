package octo.file;

import lombok.AllArgsConstructor;
import octo.file.io.CsvFileWriter;
import octo.file.io.mbb.BufferedFileWriter;
import octo.file.metadata.Metadata;
import octo.file.processor.csv.DateFormatProcessor;
import octo.file.processor.csv.ParseNumber;
import octo.file.processor.csv.StrRegExTrimmed;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvListWriter;
import org.supercsv.io.ICsvListWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

@AllArgsConstructor
public class CsvConversionTask implements Callable<String> {

  private byte[] content;
  private final String destFile;
  private final CsvMetadata csvMetadata;

  @Override
  public String call() throws Exception {
    CsvFileWriter writer = new BufferedFileWriter(destFile);
    writer.openFile();
    CellProcessor[] processors = getProcessors(csvMetadata);
    int from = 0;
    int to = 0;
    List<String> inputItems = new ArrayList<>();
    while (from < content.length) {
      for (int i = 0; i < csvMetadata.getMetadataList().size(); i++) {
        Metadata meta = csvMetadata.getMetadataList().get(i);
        to = from + meta.getLength();
        inputItems.add(new String(Arrays.copyOfRange(content, from, to)));
        from = to;
      }
      StringWriter strWriter = new StringWriter();
      ICsvListWriter csvListWriter = new CsvListWriter(strWriter, CsvPreference.STANDARD_PREFERENCE);
      try {
        csvListWriter.write(inputItems, processors);
      } catch (Exception e) {
        throw new InvalidDataException(String.format("Failed to parse data file, reason = %s", e.getMessage()));
      } finally {
        csvListWriter.close();
        csvListWriter = null;
      }
      writer.write(strWriter.toString().getBytes());
      from += Constants.CRLF.length();
      inputItems.clear();
      strWriter.getBuffer().delete(0, strWriter.getBuffer().length());
    }
    writer.finish();
    writer = null;
    content = null;
    return destFile;
  }

  private CellProcessor[] getProcessors(CsvMetadata csvMetadata) {
    List<Metadata> metadataList = csvMetadata.getMetadataList();
    final CellProcessor[] processors = new CellProcessor[metadataList.size()];
    for (int i = 0; i < metadataList.size(); i++) {
      switch (metadataList.get(i).getType()) {
        case STRING: processors[i] = new StrRegExTrimmed(Constants.CSV_STRING_PATTERN); break;
        case DATE: processors[i] = new DateFormatProcessor(Constants.RAW_FILE_DATE_FORMAT, Constants.CSV_DATE_FORMAT); break;
        default: processors[i] = new ParseNumber();
      }
    }
    return processors;
  }

}
