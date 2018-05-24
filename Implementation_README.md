# Fixed File Format Convertor Solution:

This project is an implementation of a fixed file format convertor that convertes a fixed file format into a csv file format with a given metadata file.

## Build Steps

The project can be built using gradle. The following command will build the project

./gradlew clean build

## Artifacts

The above build step generates a fffc-1.0-SNAPSHOT.jar under build/libs directory

## Usage

The generated fffc-1.0-SNAPSHOT.jar takes three input arguments. Below are the options to be passed to executable jar

 -i,--input <arg>      input file path
 -m,--metadata <arg>   metadata file path
 -o,--output <arg>     output file path

Executing java -jar fffc-1.0-SNAPSHOT.jar will print the below help usage

usage: com.octo.fffc.FixedFileConvertor
 -i,--input <arg>      input file path
 -m,--metadata <arg>   metadata file path
 -o,--output <arg>     output file path
