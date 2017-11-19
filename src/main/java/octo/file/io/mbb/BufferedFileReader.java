package octo.file.io.mbb;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import octo.file.io.CsvFileReader;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

@Data
@RequiredArgsConstructor
@Slf4j
public class BufferedFileReader implements CsvFileReader {

  private final String fileUrl;
  private final long blockSize;

  private MappedByteBuffer buffer = null;
  private FileChannel fileChannel = null;
  private long channelSize;
  private long fromPos;

  @Override
  public void openFile() throws IOException {
    fileChannel = new RandomAccessFile(fileUrl, "r").getChannel();
    channelSize = fileChannel.size();
  }

  @Override
  public long getFileSize() throws IOException {
    return channelSize;
  }

  @Override
  public boolean hasNext() throws IOException {
    return fromPos < channelSize;
  }

  @Override
  public byte[] getNext() throws IOException {
    long thisBlockSize = blockSize;
    if (fromPos + blockSize > channelSize) {
      thisBlockSize = channelSize - fromPos;
    }
    if (fromPos < channelSize) {
      log.debug("reading next block from {}, length = {}", fromPos, thisBlockSize);
      buffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, fromPos, thisBlockSize);
      fromPos += thisBlockSize;
    } else {
      buffer = null;
    }
    return toBytes(buffer);
  }

  @Override
  public byte[] getAll() throws IOException {
    buffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, channelSize);
    return toBytes(buffer);
  }

  private byte[] toBytes(MappedByteBuffer buffer) {
    int size = buffer.limit();
    byte[] bytes = new byte[size];
    for (int i = 0; i < size; i++) {
      bytes[i] = buffer.get(i);
    }
    return bytes;
  }

}
