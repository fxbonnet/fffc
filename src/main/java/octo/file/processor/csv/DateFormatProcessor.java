package octo.file.processor.csv;

import org.supercsv.cellprocessor.CellProcessorAdaptor;
import org.supercsv.cellprocessor.FmtDate;
import org.supercsv.cellprocessor.ParseDate;
import org.supercsv.util.CsvContext;

import java.util.Date;

public class DateFormatProcessor extends CellProcessorAdaptor {

  private final ParseDate parseDate;
  private final FmtDate fmtDate;

  public DateFormatProcessor(String inFmt, String outFmt) {
    parseDate = new ParseDate(inFmt);
    fmtDate = new FmtDate(outFmt);
  }

  @Override
  public String execute(Object val, CsvContext csvContext) {
    String strVal = ((String) val).trim();
    Date date = (Date) parseDate.execute(strVal, csvContext);
    String result = (String) fmtDate.execute(date, csvContext);
    return result;
  }
}
