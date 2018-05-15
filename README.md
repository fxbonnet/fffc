# Fixed File Format Converter: How To

##Run this project tests

    `sbt test`

##Run this project on the command line

1. Valid input file example (to standard output)

    `sbt "runMain Convert --metadata <path to fffc project>/src/test/resources/metadata.csv <path to fffc project>/src/test/resources/data2.txt"`
  
2. Valid input file example (to file output)

    `sbt "runMain Convert --metadata <path to fffc project>/src/test/resources/metadata.csv --out out1.csv <path to fffc project>/src/test/resources/data2.txt"`

3. Invalid input file example (to file output)

    `sbt "runMain Convert --metadata <path to fffc project>/src/test/resources/metadata.csv --out out2.csv <path to fffc project>/src/test/resources/data2.txt"`