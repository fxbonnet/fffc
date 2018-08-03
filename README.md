# Requirements

# Fixed File Format converter

The goal is to write a generic tool to convert fixed file format files to a csv file based on a metadata file describing its structure.

Languages chosen: Python 3.7.0 

Approach: 
1. Read the data descriptor file 
2. Write the header row in the output csv
3. Read the flat file, for each row, validate and format and then write each line being read to the output file 

# Dependencies
* Python 3.7.0

# Instructions to use
* Clone repo 
* In your terminal run the following command

    ```python <FFFC_LOCATION>/file_converter.py <METADATA_CSV_FILE> <FLAT_FILE> <OUTPUT_CSV_FILE>```

    e.g. `~/workspaces/fffc $ python /home/vighy-vbox/workspaces/fffc/file_converter.py data/metadata.csv data/large_data_file data/large_data_file.csv`


## TODO
* Add more test cases
* Implement async io to speed up the process


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

