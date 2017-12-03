Fixed File Format converter

Main Class FileFormatter
This class takes the file names as input for the Metadata file and the input file. 

Output file will be generated as a CSV file. 

There are some System.out.prinln statements in the file. These can easily be replaced by log statements. It was not done as part of this project since it was out of scope for this task. 

There are some sample test files included

Github does not allow me to upload empty files. 
One of the tests uses a MetadataEmpty.txt file which is an empty file. If the project fails to execute, please create an empty MetadataEmpty.txt in the path and it should work.

MetaDataReader, CSVWriter and some utils are designed to be as separate classes. The idea behind this is not just to keep code clean, but also make it easily pluggable with other classes giving the flexibility to extend this to different formats with similar logic. 



