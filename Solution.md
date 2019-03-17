# Installation / Run / Test

## Install the program

* Clone the repository on your local machine
* From the terminal, navigate to the project root
* Type "yarn" to install all dependencies

## Run the program

* Type "yarn start" to run the program with sample files
* The result should be written in the "output.text" file in the outputs folder

## Test the program

* Type "yarn test" to test the program
* Type "yarn coverage" to get a code coverage report

# Solution

The program is written in JavaScript and is run in Node.js environment.
The main script is located in "src/app.js".
The "src/demo.js" simulates a call from an external module and is used here to run the demo program ("yarn start").
The "test" folder contains the test script. Mocha and Chai here are used respectively as a test runner and an assertion library.

# If I had more time...

## Future features

1. Although it in not an explicit requirement, I would have add command prompts to allow the user to choose the location of the files to be converted from the terminal.

2. Add a warning and a loading loop (i.e. ".", "..", "...") when the file to read exceeds a certain size (e.g. > 200MB)

3. Add some logic to ensure the "inputFile" format is correct (e.g. make sure each line follows the structure given in the structure file format)

4. Work on this requirement: "strings columns may contain separator characters like ',' and then the whole string needs to be escaped with " (double quotes). Only CR or LF are forbidden"

## Future Tests

Test coverage => 74% (industry standard is 85%).

1. Add tests to ensure errors are thrown if the input file format is wrong.

2. Add a test to ensure program handle special characters.

Contact details:

Jeremy Giraudet
jeremy.giraudet@gmail.com
https://www.linkedin.com/in/jeremygiraudet/
