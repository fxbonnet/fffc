package com.octo.techtest.metadata;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.IOUtils;

import com.octo.techtest.exception.ConvertException;

public abstract class ColumnFormatter {

	protected String name;
	protected int length;
	protected String origin;

	public static List<ColumnFormatter> initFormatters(File metadata) throws ConvertException {
		List<ColumnFormatter> formatters = new ArrayList<>();
		Reader in = null;
		try {
			in = new InputStreamReader(new FileInputStream(metadata), "UTF-8");
			Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(in);
			for (CSVRecord record : records) {
				try {
					if (record.size() != 3) {
						throw new IllegalArgumentException("Unexpected column definition: " + record.toString());
					}
					int length = Integer.parseInt(record.get(1));
					formatters.add(getInstance(record.get(0), length, record.get(2)));
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException("Unexpected column definition: " + record.toString());
				}
			}
		} catch (IOException e) {
			throw new ConvertException("Cannot read metadata file in UTF-8 format: " + metadata.getAbsolutePath(), e);
		} catch (Exception e) {
			throw new ConvertException("Error happened when reading metadata file. ", e);
		} finally {
			IOUtils.closeQuietly(in);
		}

		return formatters;
	}

	private static ColumnFormatter getInstance(String name, int length, String type) {
		if (type.equalsIgnoreCase("date")) {
			return new DateColumnFormatter(name, length);
		}
		if (type.equalsIgnoreCase("string")) {
			return new StringColumnFormatter(name, length);
		}
		if (type.equalsIgnoreCase("numeric")) {
			return new NumericColumnFormatter(name, length);
		}
		throw new IllegalArgumentException(type + " is not supported!");
	}

	protected ColumnFormatter(String name, int length) {
		super();
		this.name = name;
		this.length = length;
	}

	public String getName() {
		return name;
	}

	public String input(String content) throws ConvertException {
		if (content.length() < length) {
			throw new ConvertException("Unexpected data length.");
		}
		origin = content.substring(0, length);
		return content.substring(length);
	}

	public abstract String output() throws ConvertException;

}
