API details

Purpose- converts Fixed format file to CSV file

POST /convert/fffToCsv HTTP/1.1

Host: localhost:8080 (configured in application.properties)

Content-Type: multipart/form-data;
Content-Disposition: form-data; name="files" => choose two files: fixed format file (no extension or .txt) and metadata file (.csv)
Accepts upto 10GB of file size which is configured in application.properties

Metadata constraints

1) Should have columns separated by comma and row separated by CRLF
2) Acceptable column types - date,string,numerical - NOT case sensitive
3) order of columns matters and should be equivalent to content of FFfile.
4) file should have extension .csv

Fixed format file constraints

1) Extension is not expected but any txt equivalent file is acceptable
2) order of columns should be equivalent to metadata
3) CR or LF or encountering last column marks end of row
4) For string that are not enclosed and other column types, Space or max column length marks end of column
5) For string that are enclosed, second identifier(double quote) or max column length marks end of column
6) For string that are not enclosed, only alphebets and numbers are allowed. 
7) For string that are enclosed, all special characters except CR and LF are allowed. Including space in this space is configurable(in application.properties)
8) Input and output Date pattern is configurable in application.properties
9) String enclosing identifier(ex.double quote) is configurable in application.properties
10) Output csv delimeter (row and column) is configurable in application.properties

Check test files where above cases are used.

Working:

1)Reads input fixed format file character by character
2)Based on constraints,either adds or ignores character to form column value => stops if constraints are not met (e.g unlikely special character occurance)
3)once column end is reached, its validated based on column type. => stops if content is invalid (e.g number format error, date format error)
4)valid column is added to row. For date, as per requirement, converted to output date format.
5)once row end is reached, row is written as line to result csv


To start server - mvn clean spring-boot:run

To run tests - mvn test