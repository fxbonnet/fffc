To compile my code just run:
	python FFFtoCSV.py <metadata text file> <fixed file text> <csv text file to write to>

My assumptions are:
	- text file are the ones being read
	- days given and month given are in range
	- strings contain anything
	- metadata file is always seperated by commas
	- we can print errors to stdout and doesn't needed to be
	outputted to a file

My test cases are:
	-test0: example we were given
	-test1: Add extra columns in metadata
	-test2: Add even more columns in metadata to see if it gets everything correct
	-test3: Tested for one column with multiple lines and more ASCII
	-test4: test for special characters and adding extra space to meta
	data in order to see if it can read it.
All error handles seem correct
Expected output was inspected by hand and they seem to be correct