package octo.file;

import lombok.Data;
import octo.file.metadata.Metadata;

import java.util.Collections;
import java.util.List;

@Data
public class CsvMetadata {

  private final List<Metadata> metadataList;

  public CsvMetadata(List<Metadata> list) {
    this.metadataList = Collections.unmodifiableList(list);
  }

  public int getRecordSize() {
    int total = 0;
    for (Metadata meta : metadataList) {
      total += meta.getLength();
    }
    return total;
  }

  public String getHeaders() {
    StringBuilder sb = new StringBuilder();
    int size = metadataList.size();
    for (int i = 0; i < size; i++) {
      String header = metadataList.get(i).getName();
      sb.append(i == 0 ? header : Constants.CSV_SEPARATOR + header);
    }
    return sb.toString();
  }

}
