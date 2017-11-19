package octo.file.metadata;

import lombok.extern.slf4j.Slf4j;
import octo.file.processor.meta.ParseDatatype;
import octo.file.processor.meta.ParseLength;
import octo.file.util.FileUtil;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MetadataParser {

  private CellProcessor[] getProcessors() {
    final CellProcessor[] processors = new CellProcessor[]{
        // name, length, type
        new UniqueHashCode(), new ParseLength(), new ParseDatatype()
    };
    return processors;
  }

  public List<Metadata> parse(String fileName) throws IOException, URISyntaxException {
    String resolvedFile = FileUtil.getAbsolutePath(fileName);
    List<Metadata> metadataList = new ArrayList<>();
    ICsvBeanReader beanReader = null;
    try {
      beanReader = new CsvBeanReader(new FileReader(resolvedFile), CsvPreference.STANDARD_PREFERENCE);
      final CellProcessor[] processors = getProcessors();
      final String[] headers = {"name", "length", "type"};
      Metadata meta;
      while ((meta = beanReader.read(Metadata.class, headers, processors)) != null) {
        metadataList.add(meta);
      }
    } finally {
      if (beanReader != null) {
        beanReader.close();
      }
    }
    return metadataList;
  }

}
