package octo.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class FileConverter {

	public static void main(String[] args) {
		String csvFile = "resources/metadata.csv";
		String fromFile = "resources/data.dat";
		String toFile = "resources/output.dat";

		CSVUtil csvUtil = new CSVUtil();
		FileConverter conv = new FileConverter();

		try {
			List<CsvColumn> cols = csvUtil.getFileColumn(csvFile);
			conv.convert(fromFile, toFile, cols);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private void convert(String fromPath, String toPath, List<CsvColumn> cols) throws IOException, ParseException {
		File toFile = new File(toPath);

		BufferedReader bufferReader = new BufferedReader(new FileReader(fromPath));
		// clean the data
		FileUtils.writeStringToFile(toFile, "", false);

		writeCsvHeader(toFile, cols);
		String line = null;

		while ((line = bufferReader.readLine()) != null) {
			String lineToWrite = convertLineToCsvLine(line, cols);
			FileUtils.writeStringToFile(toFile, lineToWrite.toString(), true);
		}
	}

	public String convertLineToCsvLine(String line, List<CsvColumn> cols) {
		StringBuilder sb = new StringBuilder();
		Iterator<CsvColumn> it = cols.iterator();
		int start = 0;
		String prefix = "";
		while (it.hasNext()) {
			CsvColumn col = it.next();
			String data = line.substring(start, start + col.getLength()).trim();
			if (col.getFormat().equals("date")) {
				SimpleDateFormat fromFormat = new SimpleDateFormat("yyyy-mm-dd");
				SimpleDateFormat toFormat = new SimpleDateFormat("dd/mm/yyyy");
				try {
					Date d = fromFormat.parse(data);
					data = toFormat.format(d);
				} catch (ParseException e) {
					System.out.println("Date format exception: " + e);
					throw new RuntimeException(e);
				}
			} else {
				if (data.indexOf(",") != -1) {
					data = "\"" + data + "\"";
				}
			}
			sb.append(prefix).append(data);
			prefix = ",";
			start += col.getLength();
		}
		sb.append("\r\n");
		return sb.toString();
	}

	public void writeCsvHeader(File toFile, List<CsvColumn> cols) {
		String prefix = "";
		Iterator<CsvColumn> it = cols.iterator();
		try {
			while (it.hasNext()) {
				CsvColumn col = it.next();
				String str = col.getFieldName();
				if (str.indexOf(",") != -1) {
					str = "\"" + str + "\"";
				}
				str = prefix + str;
				FileUtils.writeStringToFile(toFile, str, true);
				prefix = ",";
			}
			FileUtils.writeStringToFile(toFile, "\r\n", true);
		} catch (IOException e) {
			throw new RuntimeException("File not found: " + toFile);
		}
	}
}
