/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file_converter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author Nayeem Hasan
 */
public class Main {
    
    public static ArrayList<MetaData> list = new ArrayList<MetaData>();

	public static void main(String[] args) throws IOException {

		PrintWriter out = new PrintWriter(new File("csv.txt"), "UTF-8");
		File data = readData("data.txt");
		File metaData = readData("metadata.txt");

		// printData(data);
		// printData(metaData);

		dataCount(metaData);

		if (list.size() != 0) {

			String str;
			String res;
			String line;
			
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(data), "UTF8"));
			out.println(getDataHeader(list));
			while ((str = in.readLine()) != null) {
				int start = 0;
				int end = 0;
				res = "";
				
				// System.out.println(str);
				for (int i = 0; i < list.size(); ++i) {
					// System.out.println(list.get(i).columnName);
					end = start + list.get(i).length;
					
					//System.out.println(">>>>>>> "+list.get(i).columnName);
					if (list.get(i).type.equalsIgnoreCase("date")) {
						line = str.substring(start, end).trim();
						String tmp [] = line.split("-");
						line = tmp[2] +"/"+ tmp[1] +"/"+ tmp[0];
						//System.out.println(">>>>>>> "+line);
						
					} else {
						line = str.substring(start, end).trim();
					}
					
					if (line.contains(",")) 
						line = "\""+line+"\"";
					

					if (i != list.size() - 1)
						res += line + ",";
					else
						res += line;

					start = end;

					 
				}
				//System.out.println(res);
				out.println(res);
			}

		} else {
			System.out.println("No metadata found");
		}
		 out.close();

	}

	public static File readData(String fileName) {
		return new File(fileName);
	}

	public static void dataCount(File file) {
		
		try {
			String str;
			String tmp[];
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
			while ((str = in.readLine()) != null) {
				tmp = str.trim().split(",");
				MetaData md = new MetaData(tmp[0], Integer.parseInt(tmp[1]), tmp[2]);
				list.add(md);
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.print("Can't read the file");
			System.exit(1);
		}

		
	}

	public static void printData(File file) {
		try {
			String str;
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
			while ((str = in.readLine()) != null) {
				System.out.println(str);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	
	public static String getDataHeader (ArrayList<MetaData> list) {
		
		String header = "";
		for (int i = 0; i < list.size(); ++i) {
			if (i != list.size() - 1)
				header += list.get(i).columnName + ",";
			else
				header += list.get(i).columnName;
			
		}
		
		return header;
		
		
	}

    
}
