Prerequisite:
This program was developed with Pycharm and Python 3.7. It assumes the latest Anaconda 3.7 package has been installed.

Performance Consideration:
The specification of metadata was read and hold in MetaDataHandler so that it can be reused.
The content of the file was read and write line by line so it can work with large file.

To run the programme please do:
    python fffc.py
    
All unit tests are included. If there's any content issue in parsing the source file, an ValueError would be raised with
 information about the location, content of the failed column. This can be demonstrated by changing the fffc.py, and 
 pointing the input file to data_wrong_format.fff. This serves as the integration test as well.  
