# Fixed File Format converter

This tool converts a Fixed Format File to CSV, can be further extended to convert from a Fixed file format to other formats such as XML, JSON, YAML etc.

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

## Design

I have used java 8 with spring-boot and created a micro-service which exposes the below endpoint to perform the conversion:

To run this micro-service, after cloning the repo, execute the below command from the base directory  (as a Pre-req maven and java 8 should be installed on your machine):

```
mvn spring-boot:run

```

After the application is started, to convert a file send the below http request to the running app on localhost:8080

POST Request to: http://localhost:8080/convert with the below json body :

```
{
"dataFilePath" : "src/test/resources/files/data.txt", 
"metadataFilePath" : "src/test/resources/files/metadata.txt", 
"outputFileName" : "src/test/resources/files/output.txt",
"formatType": "csv"
}
```

* DataFilePath: relative/absolute path to the fixed format file
* metadataFilePath: relative/absolute path to the metadata file
* outputFileName: relative/absolute name of the file to be created after the conversion
* formatType: [csv] as of now only supports csv

You can either use Postman,RestClient etc to test this microservice.
