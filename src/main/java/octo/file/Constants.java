package octo.file;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {
  public static final char CSV_SEPARATOR = ',';
  public static final char DOUBLE_QUOTE = '\"';
  public static final String CRLF = "\r\n";

  public static final String RAW_FILE_DATE_FORMAT = "yyyy-mm-dd";
  public static final String CSV_DATE_FORMAT = "dd/mm/yyyy";
  public static final String CSV_STRING_PATTERN = ".*";
}
