# Fixed File Format converter

Generic tool to convert fixed file format files to a csv file based on a metadata file describing its structure.

Language used: 'java 8'

## How to run the application

Run:
```
gradle run -Pargs="${FULLY_QUALIFIED_PATH_TO_METADATA_FILE} ${FULLY_QUALIFIED_PATH_TO_INPUT_DATA_FILE}"
```

The generated output file will have the same name as the input data file, excluding the file extension. Example:

Running
Run:
```
gradle run -Pargs="metadata.txt data.txt"
```
will create a file named `data.csv`



## Assumptions
* Decimal numbers with trailing zeros may have its trailing zeros dropped. If this needs to be kept in can be amended in a future version.
* There is no parallel processing because the order of the input data files needed to be kept. If this is not important, parallel processing could be used to convert the file a bit faster in a server with multiple cores.
* The output file is being written line by line to avoid loading large volume of data in memory, but buffering could be used if necessary (i.e. writting to the file in tranches).
