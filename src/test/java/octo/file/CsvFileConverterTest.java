package octo.file;

import octo.file.util.FileUtil;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class CsvFileConverterTest {

  private CsvFileConverter c = new CsvFileConverter();

  @Test
  public void shouldConvertGoodFile() throws Exception {
    String csvFile = Paths.get(getTestDirectory().toString(), "octo/file/out/data1").toFile().getAbsolutePath();
    c.convert("/octo/file/data1", "/octo/file/meta1", csvFile);
    File actual = new File(csvFile);
    assertThat(actual, notNullValue());
    assertThat(actual.exists(), is(true));
    assertFileContent(Arrays.asList(
        "Birth date,First name,Last name,Weight",
        "01/01/1970,John,Smith,81.5",
        "31/01/1975,Jane,Doe,61.1",
        "28/11/1988,Bob,Big,102.4"
    ), csvFile);
  }

  @Test
  public void shouldConvertFileWithSpecialCharacters() throws Exception {
    String csvFile = Paths.get(getTestDirectory().toString(), "octo/file/out/data1_special_character").toFile().getAbsolutePath();
    c.convert("/octo/file/data1_special_character", "/octo/file/meta1", csvFile);
    File actual = new File(csvFile);
    assertThat(actual, notNullValue());
    assertThat(actual.exists(), is(true));
    assertFileContent(Arrays.asList(
        "Birth date,First name,Last name,Weight",
        "01/01/1970,\"Jo,hn\",\"Sm\"\"ith\",81.5",
        "31/01/1975,Jane➤,\uD83D\uDE00oe,61.1",
        "28/11/1988,Bob,Big,102.4"
    ), csvFile);
  }

  @Test(expected = Exception.class)
  public void shouldNotConvertIfHaveInvalidDate() throws Exception {
    String csvFile = Paths.get(getTestDirectory().toString(), "octo/file/out/data1_invalid_date").toFile().getAbsolutePath();
    c.convert("/octo/file/data1_invalid_date", "/octo/file/meta1", csvFile);
  }

  @Test(expected = Exception.class)
  public void shouldNotConvertIfHaveInvalidNumber() throws Exception {
    String csvFile = Paths.get(getTestDirectory().toString(), "octo/file/out/data1_invalid_number").toFile().getAbsolutePath();
    c.convert("/octo/file/data1_invalid_number", "/octo/file/meta1", csvFile);
  }

  @Test
  public void shouldConvertHugeFile() throws Exception {
    String hugeFile = Paths.get(getTestDirectory().toString(), "octo/file/out/data1_huge_in").toFile().getAbsolutePath();
    String csvFile = Paths.get(getTestDirectory().toString(), "octo/file/out/data1_huge_out").toFile().getAbsolutePath();
    File prototypeFile = new File(FileUtil.getAbsolutePath("/octo/file/data1"));
    long fileSize = 3123456789L;  // 3GB
    long t1 = System.currentTimeMillis();
    makeHugeFile(prototypeFile, hugeFile, fileSize);
    long t2 = System.currentTimeMillis();
    System.out.format("Took %d ms to produce the huge file\n", (t2 - t1));
    c.convert(hugeFile, "/octo/file/meta1", csvFile);
    long t3 = System.currentTimeMillis();
    File actual = new File(csvFile);
    assertThat(actual, notNullValue());
    assertThat(actual.exists(), is(true));
    System.out.format("Took %d ms to convert the huge file\n", (System.currentTimeMillis() - t2));
  }

  private void assertFileContent(List<String> expected, String actualFile) throws IOException {
    try (Stream<String> stream = Files.lines(Paths.get(actualFile))) {
      List<String> actual = stream.collect(Collectors.toList());
      assertThat(actual, is(expected));
    }
  }

  private Path getTestDirectory() throws Exception {
    return Paths.get(getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
  }

  // This method copy the src file to the dest file repeatedly till the given fileSize reached
  private void makeHugeFile(File srcFile, String destFileName, long fileSize) throws Exception {
    File destFile = new File(destFileName);
    if (destFile.exists()) {
      if (!destFile.delete()) {
        fail("The file with " + destFileName + " already exists and can't be deleted");
      }
    }
    destFile.createNewFile();
    RandomAccessFile randomAccessFile = new RandomAccessFile(destFileName, "rw");
    FileChannel fileChannel = randomAccessFile.getChannel();
    byte[] content = FileUtils.readFileToByteArray(srcFile);

    int contentWritesPerThead = 100000;
    long threads = fileSize / (srcFile.length() * contentWritesPerThead) + 1;
    threads = threads > 100 ? 100 : threads;
    ExecutorCompletionService<String> completionService = new ExecutorCompletionService<>(Executors.newFixedThreadPool((int) threads));
    List<Future<String>> futures = new ArrayList<>();
    long written = 0L;
    int count = 1;
    while (written < fileSize) {
      final String fn = destFileName + "_" + count;
      Future<String> future = completionService.submit(() -> {
        RandomAccessFile tmpFile = new RandomAccessFile(fn, "rw");
        FileChannel fc = tmpFile.getChannel();
        for (int i = 0; i < contentWritesPerThead; i++) {
          fc.write(ByteBuffer.wrap(content));
          fc.write(ByteBuffer.wrap(Constants.CRLF.getBytes()));
        }
        fc.close();
        fc = null;
        return fn;
      });
      futures.add(future);
      count++;
      written += (content.length + Constants.CRLF.length()) * contentWritesPerThead;
    }
    List<String> files = new ArrayList<>();
    for (Future<String> future : futures) {
      files.add(future.get());
    }
    for (String fn : files) {
      File file = new File(fn);
      fileChannel.map(FileChannel.MapMode.READ_WRITE, fileChannel.size(), file.length()).put(FileUtils.readFileToByteArray(file));
      file.delete();
      file = null;
    }

    fileChannel.close();
    fileChannel = null;
    destFile = Paths.get(destFileName).toFile();
    assertThat(destFile, notNullValue());
    assertTrue(destFile.exists());
    assertTrue(destFile.length() >= fileSize);
  }
}
