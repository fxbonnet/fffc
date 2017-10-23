package com.fffc;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Application entry point for shell execution.
 * 
 * It accepts the following arguments:
 * -m (--meta-file) - Full path to the metadata file.
 * -d (--data-file) - Full path to the data file.
 * -c (--csv-file) - Full path to the output csv file.
 * 
 * <b>Note: if either of the arguments above is not provided
 * the application will exit with an error. 
 * </b>
 * 
 */
public class Main 
{
	
	/*
	 * Argument definitions for easier parsing.
	 * If another argument is to be added, feel free to put it here. 
	 */
	private enum Arg {
		META("m", "meta-file", "Full path to the metadata file."),
		DATA("d", "data-file", "Full path to the data file."),
		CSV("c", "csv-file", "Full path to the output csv file.");
		
		private String name;
		private String longName;
		private String desc;
		
		private Arg(String name, String longName, String desc) {
			this.name = name;
			this.longName = longName;
			this.desc = desc;
		}
		
		protected String arg() {
			return name;
		}
		
		protected String longName() {
			return longName;
		}
		
		protected String desc() {
			return desc;
		}
	}
	
	public static void main( String[] args ) throws ParseException
    {
		CommandLine cmd = parseArgs(args);
    	String metaFilePath = cmd.getOptionValue(Arg.META.arg());
    	String dataFilePath = cmd.getOptionValue(Arg.DATA.arg());
    	String csvFilePath = cmd.getOptionValue(Arg.CSV.arg());
    	    	
    	FixedFileFormatConverter trans = new FixedFileFormatConverter(
    			metaFilePath, dataFilePath, csvFilePath);
    	trans.convert();
    	
    }
    
    private static CommandLine parseArgs(String[] args) {
    	Options options = new Options();
    	for(Arg a : Arg.values()) {
    		options = options.addOption(
    				Option.builder(a.arg())
    				.longOpt(a.longName())
    				.hasArg().desc(a.desc()).build());
    	}
    	
    	CommandLineParser parser = new DefaultParser();
    	CommandLine cmd = null;
    	try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
    	
    	return cmd;
    }
}
