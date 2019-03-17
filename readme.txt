The project is a simple java application.

#########################before running it###################
it requires two extra jar except the jdk 1.7.
The two jars are located in the following path:
path: FixedFileFormatConverter/extralibs 
when open it in the IDE. You may need to find the jars below above folder
to manually add it to the build path.

Step 1: Right click the jar -> 
Step2: select build path in the popping up menu.
Step 3: choose the add to build path option.

########################how to run it #################
Go to the FixedFileFormatConverterMain.java file 
location: FixedFileFormatConverter/com/octo/code/practice/portal
Right click the the file name and run it as `java application`.


##################################More information ####################
String dataFilePath = "src\\com\\octo\\code\\practice\\resource\\data.txt";
String metadataFilePath = "src\\com\\octo\\code\\practice\\resource\\metadata.txt";
String generatedCSVFilePath = "src\\com\\octo\\code\\practice\\resource\\output";

There are three file path variables defined in the 'FixedFileFormatConverterMain' java file.
dataFilePath: It represents the fixed data file -> the raw data source file path.
metadataFilePath: It represents the metadata file path
generatedCSVFilePath: It represents the destination of the path of generated CSV file.
