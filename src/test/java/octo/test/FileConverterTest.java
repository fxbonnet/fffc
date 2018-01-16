package octo.test;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class FileConverterTest {

	FileConverter fc = new FileConverter();
	
	@Test
	public void testConvertLineToCsvLine() {
		List<CsvColumn> cols = new LinkedList();
		CsvColumn c = new CsvColumn("Birth Date", 10, "date");
		cols.add(c);
		c = new CsvColumn("First Name", 15, "string");
		cols.add(c);
		c = new CsvColumn("Last Name", 15, "string");
		cols.add(c);
		c = new CsvColumn("Weight", 5, "string");
		cols.add(c);
		
		String line = "1970-01-01John           Smith           81.5";
		String csvLine = fc.convertLineToCsvLine(line, cols);
		assertEquals(csvLine, "01/01/1970,John,Smith,81.5\r\n");
	}

	
	@Test
	public void testConvertLineToCsvLineWithEscape() {
		List<CsvColumn> cols = new LinkedList();
		CsvColumn c = new CsvColumn("Birth Date", 10, "date");
		cols.add(c);
		c = new CsvColumn("First Name", 15, "string");
		cols.add(c);
		c = new CsvColumn("Last Name", 15, "string");
		cols.add(c);
		c = new CsvColumn("Weight", 5, "string");
		cols.add(c);
		
		String line = "1970-01-01John,          Smith           81.5";
		String csvLine = fc.convertLineToCsvLine(line, cols);
		assertEquals(csvLine, "01/01/1970,\"John,\",Smith,81.5\r\n");
	}
	
	
	@Test(expected = RuntimeException.class)
	public void testConvertLineDateFormatException() {
		List<CsvColumn> cols = new LinkedList();
		CsvColumn c = new CsvColumn("Birth Date", 10, "date");
		cols.add(c);
		
		String line = "1970-01/91";
		fc.convertLineToCsvLine(line, cols);
	}
}
