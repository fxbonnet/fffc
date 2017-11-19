package octo.file.processor.meta;

import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.exception.SuperCsvConstraintViolationException;
import org.supercsv.util.CsvContext;

public class ParseLength extends NotNull {

  public Integer execute(Object val, CsvContext csvContext) {
    String strVal = (String) super.execute(val, csvContext);
    strVal = strVal.trim();
    Integer intVal = null;
    try {
      intVal = Integer.parseInt(strVal);
    } catch (NumberFormatException e) {}
    if (intVal == null || intVal < 0) {
      throw new SuperCsvConstraintViolationException("invalid length encountered: " + strVal, csvContext, this);
    }
    return intVal;
  }
}
