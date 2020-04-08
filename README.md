# Fixed File Format converter

## Command Line Interface
The CLI takes 3 arguments
* --input_path or -i (string, default 'input.txt'): path towards the input "raw" file.
* --meta_path or -m (string, default 'meta.txt'): path towards the metadata file.
* --output_path or -o (string, default 'output.csv'): path towards the output csv file after conversion (created if not found).

Accordingly, a valid call could be ```python file_converter.py -i path/to/input.txt --output path/to/output.csv -m path/to/meta.txt```, or the simple ```python file_converter.py``` if the 3 files are named after the default and located at the root.

## Execution Environnement
This project uses Python 3.7.3 (and does not use any external package). If you encounter a problem running it, then installing the dependencies with ```pip install -r requirements.txt ``` should solve it.

## Implementation Choices
Several checks are made on the input and metadata files, and raise an exception if they fail.

The only check performed on the metadata file is that the number of fields per line should be exactly 3 (name, size and type). 

Several checks are made on the input raw file :
* The length of each line should be the same, which is actually the sum of the length the fields.
* Spaces are not allowed 'inside' the field, in such a way that '  bob ' is valid but ' b ob ' is not.
* Dates should be in the correct specified format yyyy-mm-dd.

## Tests






