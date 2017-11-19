package octo.file;

import lombok.extern.slf4j.Slf4j;
import octo.file.io.CsvFileReader;
import octo.file.io.CsvFileWriter;
import octo.file.io.mbb.BufferedFileWriter;
import octo.file.io.mbb.BufferedFileReader;
import octo.file.metadata.MetadataParser;
import octo.file.util.FileUtil;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
public class CsvFileConverter {

  private static final long MAX_BATCH_BYTES = 50000000L; // 50MB

  MetadataParser metadataParser = new MetadataParser();

  public void convert(String rawFile, String metadataFile, String csvFile)
      throws InvalidDataException,
      IOException,
      ParseException,
      URISyntaxException,
      ExecutionException, InterruptedException {

    CsvMetadata csvMetadata = new CsvMetadata(metadataParser.parse(metadataFile));

    int size = csvMetadata.getRecordSize() + Constants.CRLF.length();
    int batchSize = calculateBatchSize(size);
    long actualBatchBytes = size * batchSize;

    CsvFileReader reader = new BufferedFileReader(FileUtil.getAbsolutePath(rawFile), actualBatchBytes);
    reader.openFile();

    CsvFileWriter writer = new BufferedFileWriter(FileUtil.getAbsolutePath(csvFile));
    writer.openFile();
    writer.write(csvMetadata.getHeaders().getBytes());
    writer.write(Constants.CRLF.getBytes());

    // multi-threads to write if the source file is too big
    long fileSize = reader.getFileSize();
    int batchCount = (int) (fileSize / actualBatchBytes);
    if (fileSize % actualBatchBytes != 0) {
      batchCount++;
    }
    int threads = batchCount > 100 ? 100 : batchCount;
    ExecutorService executor = Executors.newFixedThreadPool(threads);
    ExecutorCompletionService<String> completionService = new ExecutorCompletionService<>(executor);
    log.debug("Using {} thread(s) to process {}, file size = {}", threads, rawFile, fileSize);
    List<Future<String>> futures = new ArrayList<>();
    int count = 1;
    while (reader.hasNext()) {
      byte[] content = reader.getNext();
      String destFile = csvFile + "_" + count;
      Future<String> future = completionService.submit(new CsvConversionTask(content, destFile, csvMetadata));
      futures.add(future);
      count++;
    }

    // join the temp csv files together
    List<String> completedFiles = new ArrayList<>();
    for (Future<String> future : futures) {
      completedFiles.add(future.get());
    }

    for (String tempFile : completedFiles) {
      BufferedFileReader tempFileReader = new BufferedFileReader(tempFile, 0L);
      tempFileReader.openFile();
      byte[] completedCsvBytes = tempFileReader.getAll();
      writer.write(completedCsvBytes);
      tempFileReader = null;
      completedCsvBytes = null;
      Paths.get(tempFile).toFile().delete();
    }

    writer.finish();
  }

  private int calculateBatchSize(int recordSize) {
    return (int) (MAX_BATCH_BYTES / recordSize);
  }

}
