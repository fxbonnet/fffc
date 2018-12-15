# Fixed File Format converter


#Design
There are four aspects of functionality:
 1. File Reading
 2. File Processing
 3. MetaData Reading
 4. File Writing
 
 Each of the above aspect is made generic and injectable to the service. Since the input files could be large, the file is read progressively in configurable size of batches.
 The processing and writing is hence performed on the input batch.
 
 The application can be run by executing RunMain class. The class bootstraps spring application context and invokes the service.
 The input could have been very well scanned and taken dynamically but for demo purpose and to be self-contained,the input refers to the files in resources folder.
 
 #Functional Test
 The functional tests are data driven with test data sets located at following path:
 src/test/resources/testData
 
 Each data set contains:
 1. Input file
 2. Metadata file
 3. Expected output file
 4. File describing the test case  
 
 The tests can be run by executing:
 mvn test (as usual)
 or by executing FixedFileFormatConversionServiceFunctionalTest if running from IDE.
 
 #Unit Test
FormatConvertHelperTest is written as a sample unit test. 


#Task
Your goal is to write a generic tool to convert fixed file format files to a csv file based on a metadata file describing its structure.

Feel free to use your favorite language and libraries if needed (but no proprietary libraries, only open source), fork this project and provide your complete code as a pull request (including source and tests).

## Use case

Our fixed file format files can have any number of columns
A column can be of 3 formats:
* date (format yyyy-mm-dd)
* numeric (decimal separator '.' ; no thousands separator ; can be negative)
* string

The structure of the file is described in a metadata file in csv format with a line for each column defining:
* column name
* column length
* column type

You should transform the file to a csv file (separator ',' and row separator CRLF)

The dates have to be reformatted to dd/mm/yyyy

The trailing spaces of string columns must be trimmed

The csv file must include a first line with the columns names

## Example

Data file:
```
1970-01-01John           Smith           81.5
1975-01-31Jane           Doe             61.1
1988-11-28Bob            Big            102.4
```

Metadata file:
```
Birth date,10,date
First name,15,string
Last name,15,string
Weight,5,numeric
```

Output file:
```
Birth date,First name,Last name,Weight
01/01/1970,John,Smith,81.5
31/01/1975,Jane,Doe,61.1
28/11/1988,Bob,Big,102.4
```

## Extra requirements
* files are encoded in UTF-8 and may contain special characters
* strings columns may contain separator characters like ',' and then the whole string needs to be escaped with " (double quotes). Only CR or LF are forbidden
* in case the format of the file is not correct, the program should fail but say explicitly why
* a fixed format file may be very big (several GB)

