package octo.file.io;

import java.io.IOException;

public interface CsvFileWriter {

  void openFile() throws IOException;
  void write(byte[] content) throws IOException;
  void finish() throws IOException;
}