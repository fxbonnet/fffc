# Fixed File Format Converter Tool


Description
-----------

- This is a Fixed File Format Converter Utility. It has been implemented using Java language. It also uses the open source univocity-parsers.

Assignment tasks
----------------

> For the problem statement details, please refer to README.md 

To build
--------
> Install following minimum required artifacts
> > JDK 1.8

> > Apache Maven 3.3.9

> Execute the maven goals 
> > mvn clean install


To run
------
> Execute the following bat file
> > D:\FixedFileFormatConverter>runFixedFileFormatConverter.bat c:\\temp\\data.csv  c:\\temp\\metadata.csv  c:\\temp\\output.csv
> > 
> > The implemented solution uses the shaded jar "fileformatconverter-0.0.1-SNAPSHOT-shaded.jar". 
> > It contains the dependent jars i.e. univocity-parsers and allow users to run the executable jar file.


 

Tests
------
> Execute the maven command
> Note: the JUnit tests expects the files as follows:
> 1. c:\\temp\\metadata.csv
> 2. c:\\temp\\metadataInvalid.csv
> > mvn test

> Refer to JUnit tests for more details.
> (Note: At present the test coverage is limited but it can be easily expanded cover the overall functionality that this tool provides.)

Open Source Utility - univocity-parsers
-------------------------------
It is an open source utility having Apache license 2.0.
This utility has been used to implement the generic tool to convert fixed file format files to a csv file based on a metadata file describing its structure.

https://www.univocity.com/pages/univocity_parsers_download

