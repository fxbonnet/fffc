# Fixed File Format converter

A generic tool to convert fixed file format files to a csv file based on a metadata file describing its structure.

The tool is written in java and delivered as an executable jar and shell script that can be run from the command line.

To run the tool on Windows, Linux or OSX you will need to have Java version 1.8+ installed.

## Usage example

```
./fffc.sh /Users/alan/dev/fffc/testFiles/metaData1.csv /Users/alan/dev/fffc/testFiles/fixedFile1.txt /Users/alan/dev/fffc/testFiles/output2.csv
```
or
```
java -jar fffc.jar /Users/alan/dev/fffc/testFiles/metaData1 /Users/alan/dev/fffc/testFiles/fixedFile1.txt /Users/alan/dev/fffc/testFiles/output.csv
```
Note: Dates are reformatted from yyyy-mm-dd to dd/mm/yyyy

## Example files

The fixed file format files can have any number of columns
A column can be of 3 formats:
* date (format yyyy-mm-dd)
* numeric (decimal separator '.' ; no thousands separator ; can be negative)
* string

The structure of the file is described in a metadata file in csv format with a line for each column defining:
* column name
* column length
* column type


You should transform the file to a csv file (separator ',' and row separator CRLF)

Note: The output CSV file's first line has the columns names

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

