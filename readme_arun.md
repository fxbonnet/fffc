The program takes 3 arguments.
```
1. Path to Meta Data File.
2. Path to the Fixed File Format file.
3. Path to output file.
```

* The project is structured as a maven project.
* No commercial libraries have been used.
* I have used Java 8 and Intellij to develop this solution (that is what was available to me).
* You need to have maven configured on the machine to build this project.

### Build Instructions
```
mvn clean package
```
### To run the program
```
com.arun.octo.Runner META_DATA_FILEPATH DATA_FILEPATH OUTPUT_FILEPATH
```

