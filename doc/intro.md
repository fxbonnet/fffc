## Run the project

Solution implemented using the language Clojure and the build tool Leiningen.

### Option 1: Run with Leiningen

#### Required tools

* JDK 9              
* Leiningen (https://leiningen.org/)
         
         
#### How to run?

* In the command prompt go inside the root folder (fffc)
* Start the REPL 
```
lein repl
```
* execute the main function - first argument is the meta and the second one is the data
```
(-main "resources/test/meta/meta-1.csv" "resources/test/source/data-1.txt")
```

### Option 2: Run with the standalone jar

#### Required tools

* JDK 9 

A standalone jar (fffc-0.1.0-SNAPSHOT-standalone.jar) is provided in the root. Run the following command to run it - - first argument is the meta and the second one is the data

```
java -jar fffc-0.1.0-SNAPSHOT-standalone.jar "resources/test/meta/meta-1.csv" "resources/test/source/data-1.txt"
```
