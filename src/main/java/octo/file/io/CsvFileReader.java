package octo.file.io;

import java.io.IOException;

public interface CsvFileReader {

  void openFile() throws IOException;
  long getFileSize() throws IOException;
  boolean hasNext() throws IOException;
  byte[] getNext() throws IOException;
  byte[] getAll() throws IOException;
}