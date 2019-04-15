# Fixed File Format converter

## Introduction

This application is written in Java 8. It performs below functions.

* This application creates a new csv file using the provided metadata file.
* It streams each line, parse it and write it to the output file simentaneously.
* It provide two ways of handleing error scenarios. By passing the 4th argument as true/false it manages the error scenario.
  * By throwing the exception if a record is not in correct format.
  * By writing the error record into error file and moving on to the next line.
  
## How to use the Application

This application takes 4 arguments as below.

* Argument 1 - Manadatory - Complete Path of the Metadata File
* Argument 2 - Manadatory - Complete Path of the Input File in Fixed File Format.
* Argument 3 - Manadatory - Complete Path for the output file.
* Argument 4 - Optional - "true"/"false" (default "false")
   * "true" - If the argument is mentioned as true, the program will write the error records into "<Argument3>".error file, and it will go on to the next line to process the records.
   * "false" - In this case, the program will throw the exception about the problem with the file/line, and it will terminate.
  
## Future Possible Improvements 
* Program to have configuration file from where it takes the parameters.
* Program to have defined input directory, and it should poll the directory for any new input file to process and generate the output files in the output directory.


## How to Build and Run.

To Build the pacakge 

*mvn clean compile assembly:single*

To Run the application

*java -jar {target_dir}/Fixed-File-Format-Converter-0.0.1-SNAPSHOT.jar <Argument 1> <Argument 2> <Argiment 3> <"true"|"false">*

## Testing 

Most of the comman scnearios have been validated with junit and manual testing. Test src is included in the repository.

  * Records containg "," in the string fields.
  * Incorrect Date format.
  * Incorrect Fixed Format.
  * Metadata file validation.
  * Records having CR or LF in the data.

  



