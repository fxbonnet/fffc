package octo.file.processor.meta;

import octo.file.metadata.Datatype;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.exception.SuperCsvConstraintViolationException;
import org.supercsv.util.CsvContext;

public class ParseDatatype extends NotNull {

  @Override
  public Datatype execute(Object val, CsvContext csvContext) {
    String strVal = ((String) super.execute(val, csvContext)).trim();
    Datatype type = Datatype.getByName(strVal);
    if (type == null) {
      throw new SuperCsvConstraintViolationException("invalid data type encountered: " + strVal + ", must be string, date, numeric", csvContext, this);
    }
    return type;
  }
}
