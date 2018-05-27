# Instructions

* After doing  mvn clean compile, Run FixedFileFormatConverter.java under src/main/java/octo package to start the springboot application.
* You can reach the swagger ui by accessing 
  ```
  http://localhost:8080/swagger-ui.html
  ```
* Expand the /convert api and specify inputFilePath, metadataFilePath and outputFile.
Example:

    ```
        inputFilePath       = files/input
        metadataFilePath    = files/metadata.csv
        outputFilePath      = files/output.csv
    ```
* Alternatively you can do the conversion using curl as the below example
  ```
  curl -X GET --header 'Accept: */*' 'http://localhost:8080/convert?inputFilePath=files%2Finput&metadataFilePath=files%2Fmetadata.csv&outputFilePath=files%2Foutput.csv'
  ````
* You can also start the file conversion by running the MainApplication.java. But the prerequisites for running it this way are
   * The fixed format file to be converted and its corresponding metadata file should be under files folder.
   * Output file will be created under files folder and named as output.csv
* Log level can be changed in log4j.properties under resources

# Fixed File Format converter

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

