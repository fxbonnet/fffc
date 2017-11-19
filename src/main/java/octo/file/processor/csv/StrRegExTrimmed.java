package octo.file.processor.csv;

import org.supercsv.cellprocessor.constraint.StrRegEx;
import org.supercsv.util.CsvContext;

public class StrRegExTrimmed extends StrRegEx {

  public StrRegExTrimmed(String pattern) {
    super(pattern);
  }

  public String execute(Object val, CsvContext context) {
    Object result = super.execute(val, context);
    String trimedResult = ((String) result).trim();
    return trimedResult;
  }
}
