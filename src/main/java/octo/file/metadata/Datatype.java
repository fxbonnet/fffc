package octo.file.metadata;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
public enum Datatype {

  DATE("date", Date.class),
  STRING("string", String.class),
  NUMERIC("numeric", BigDecimal.class);

  private String name;
  private Class clazz;

  public static Datatype getByName(String theName) {
    for (Datatype t : Datatype.values()) {
      if (t.name.equals(theName)) {
        return t;
      }
    }
    return null;
  }
}
