package octo.test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.opencsv.CSVReader;

public class CSVUtil {

	public List<CsvColumn> getFileColumn(String path) throws IOException {
		List<CsvColumn> ret = new LinkedList();
		
		CSVReader reader = new CSVReader(new FileReader(path));
        String[] line;
        while ((line = reader.readNext()) != null) {
        	CsvColumn obj = new CsvColumn();
        	obj.setFieldName(line[0]);
        	obj.setLength(Integer.parseInt(line[1]));
        	obj.setFormat(line[2]);
            //System.out.println("[Field Name= " + line[0] + ", Length= " + line[1] + " , Format=" + line[2] + "]");
        	ret.add(obj);
        }
        return ret;
	}
}
