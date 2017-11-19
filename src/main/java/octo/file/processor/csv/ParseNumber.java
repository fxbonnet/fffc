package octo.file.processor.csv;

import org.supercsv.cellprocessor.CellProcessorAdaptor;
import org.supercsv.exception.SuperCsvConstraintViolationException;
import org.supercsv.util.CsvContext;

import java.math.BigDecimal;

public class ParseNumber extends CellProcessorAdaptor {

  @Override
  public String execute(Object val, CsvContext csvContext) {
    String strVal = ((String) val).trim();
    BigDecimal num = null;
    try {
      num = new BigDecimal(strVal);
    } catch (Exception e) {}
    if (num == null) {
      throw new SuperCsvConstraintViolationException("invalid number encountered, number = " + strVal, csvContext, this);
    }
    return strVal;
  }
}
