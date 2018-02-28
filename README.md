# Implementation

See below for the problem statement.
I have implemented this with using Java 8 as the programming language of choice.

Why Java?
It is still one of the languages that I am most comfortable with, plus for this implementation am using Java 8 with functional approach.
I used lambda expressions when appropriate and also functional programming model has made it possible to reduce the number of objects
I need to create.
For example, there is a requirement to apply different formatting/rules for different types, traditionally with Java (objects focused) I can
apply various design patterns, maybe strategy or command patterns but doing with the previous versions of Java means I need to create a lot of objects
and coordinator object.
Whereas using functional approach it significantly reduces the need to create too many objects.

I also utilised Spring Boot with its Spring Shell plugin to make this application running in an CLI.


## Running the application

Prerequisites:

1. Please ensure you have JDK8 and Maven installed.

In a shell terminal (once the code has been pulled from git):

```
mvn package -DskipTests && java -jar fffc-1.0.jar
```

Once that is done you will get to the Spring shell. Sample command:

```
fff2csv --metadata-uri "file:////tmp/metadata.txt" --data-uri "file:////tmp/data.txt" --out-uri "file:////tmp/out.csv"
```

--metadata-uri is the file path to the metadata file.
--data-uri is the file path to the fixed format data file.
--out-uri is the file path to the csv output file.

Note: the uris need to be prefixed with file:// and the implementation has only been tested with file:// only.

To exit the shell prompt type 'exit'

# Problem Statements/Requirements

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

