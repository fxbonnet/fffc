package octo.file.io.mbb;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import octo.file.io.CsvFileWriter;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@Slf4j
public class BufferedFileWriter implements CsvFileWriter {

  private final String fileUrl;

  private MappedByteBuffer buffer = null;
  private FileChannel fileChannel = null;
  private String tempFileName = null;

  @Override
  public void openFile() throws IOException {
    tempFileName = fileUrl;
    File file = new File(fileUrl);
    if (file.exists()) {
      boolean deleted = file.delete();
      if (!deleted) {
        tempFileName += "_" + LocalDateTime.now().toString().replace(":", ".");
        log.warn("Can not delete {}, using temp file {}", fileUrl, tempFileName);
      }
    }
    RandomAccessFile randomAccessFile = new RandomAccessFile(tempFileName, "rw");
    fileChannel = randomAccessFile.getChannel();
  }

  @Override
  public void write(byte[] content) throws IOException {
    long size = fileChannel.size();
    buffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, size, content.length);
    buffer.put(content);
  }

  @Override
  public void finish() throws IOException {
    fileChannel.close();
    fileChannel = null;
    buffer = null;
    if (!fileUrl.equals(tempFileName)) {
      File tempFile = new File(tempFileName);
      File dest = Paths.get(fileUrl).toFile();
      dest.setWritable(true);
      if (dest.exists()) {
        dest.delete();
      }
      FileUtils.copyFile(tempFile, dest);
    }
  }
}
