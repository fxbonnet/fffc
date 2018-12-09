# Fixed File Format converter

Your goal is to write a generic tool to convert fixed file format files to a csv file based on a metadata file describing its structure.

Feel free to use your favorite language and libraries if needed (but no proprietary libraries, only open source), fork this project and provide your complete code as a pull request (including source and tests).

## Use case

Our fixed file format files can have any number of columns
A column can be of 3 formats:
* date (format yyyy-mm-dd)
* numeric (decimal separator '.' ; no thousands separator ; can be negative)
* string

The structure of the file is described in a metadata file in csv format with a line for each column defining:
* column name
* column length
* column type

You should transform the file to a csv file (separator ',' and row separator CRLF)

The dates have to be reformatted to dd/mm/yyyy

The trailing spaces of string columns must be trimmed

The csv file must include a first line with the columns names

## Example

Data file:
```
1970-01-01John           Smith           81.5
1975-01-31Jane           Doe             61.1
1988-11-28Bob            Big            102.4
```

Metadata file:
```
Birth date,10,date
First name,15,string
Last name,15,string
Weight,5,numeric
```

Output file:
```
Birth date,First name,Last name,Weight
01/01/1970,John,Smith,81.5
31/01/1975,Jane,Doe,61.1
28/11/1988,Bob,Big,102.4
```

## Extra requirements
* files are encoded in UTF-8 and may contain special characters
* strings columns may contain separator characters like ',' and then the whole string needs to be escaped with " (double quotes). Only CR or LF are forbidden
* in case the format of the file is not correct, the program should fail but say explicitly why
* a fixed format file may be very big (several GB)

## Implementation 
Intentionally over-engineered solution written as a Ruby gem. Ruby gives cross-platform support, and gem provides versioning and distribution capabilities via https://rubygems.org, local or private gem repositories (such as Nexus or Artifactory). 

Overengineering focuses on reasonable file split to ensure small functional blocks, therefore to enable easier modifications, enhancements, teamwork such as code reviews or adding new features. Other parts focus on unit tests, ensuring/enforcing recommended practices, make sure that tool works on different platforms - various flavours of Linux and Windows. Also, I just had some time over the weekend and had no idea what you'd like to see.

The solution uses Ruby's classes and mixing, unit testing is implemented with RSpec. SimpleCov is used to ensure unit tests coverage, it fails unit tests if less than 95% (or giving) coverage is set. Rubocop is used to ensure and optionally enforce recommended Ruby style guides. Rufo package ensures Ruby code formatting. Additional code analysis is done via RubyCritic. All of these tools are exposes via top-level bash scripts so that it is easy to run them locally, under Docker container or under any kind of CI server.

Ruby's guard is used to enable continuous testing. Every time a ruby file is changed, a corresponding unit test is run. That how we can have immediate feedback on the changes we do.

Integration testing is implemented with Test Kitchen. Docker and Vagrant drivers are used to support CentOS, Ubuntu, and Windows 2012R2/2016 platforms. Test suite installs octo-fffc gem on the target OS, maps test data folder and executes octo-fffc tool against test data. That is how we sure that (1) gem can be installed on target platforms and (2) it can actually pricess data.

Last but not least, octo-fffc tool comes in two flavours: as a Ruby gem and as a Docker tooling container. You can map a local folder into the tooling container and pass  octo-fffc tool options. Not only one can use  octo-fffc on Windows/Linux, but it is also Docker-friendly and can be distributed as a Docker image.

### Local development experience 
Development can be done with any kind of editor you like. Top project folder contains a set of bash scripts "run-xxx.sh" to support editor-independent development experience.

* run-dev.sh - builds/starts a developer Docker container with guard 
* run-rubocop.sh - runs rubocop, recommended ruby styleguide, against source code
* run-rubycritic.sh - runs rubycritic, source code analysis, against source code
* run-rufo.sh - runs rufo, a ruby formatting tool, against source code
* run-tests.sh - executes all unit tests under dev Docker container
* run-kitchen.sh - builds gems, then executes kitchen tests on win/linux platforms
* run-build-gem.sh - builds octo-fffc gem o
* run-build-tool-container.sh - builds octo-fffc tooling container with gem pre-built into it

Normally, here is how the dev workflow looks like:
* create a feature branch
* run run-dev.sh to start up continuous unit testing with guard
* write new features/fixes
* execute all unit tests - run-tests.sh
* execute all integration tests - run-kitchen.sh 

Current coverage is set to 95% so that unit tests will fail with anything below. Optionally, review code quality with rubocop, rubycritic, and then run "run-rufo.sh" to ensure Ruby code formatting.

All this experience can and should be integrated into CI server.

### Gem packaging 
run-build-gem.sh packs a Ruby gems under /src/.build folder. This folder is used for Docker tool container along with Test Kitchen tests. Publishing to gem repositories is to be implemented.

### Source code structure
Source code is ogrginazed into several top level folders, and then a standard gem folder strauture under /src:
* /src/octo-fffc - ruby gem itself
* /src/octo-fffc/*.sh - helpers which are executed under DOcker cintainers
* /src/octo-fffc/Guardfile - support for guard and continious testing

Further structure under /src/octo-fffc is a standard Ruby gem package.

The second part of the repository is regression testing with Test Kitchen:
* tests/integration

A top-level .kitchen.yml defined several Linux/windows platforms and then executes corresponding tests against them. Kitchen maps several folders under Docker/Vagrant hosts to deliver gem package, test data and exposes test scripts. Currently, ubuntu/centos7, Windows 2016 and Windows 2012R2 are tested - gem install and tool execution against test data.

Top level folder contains the rest of the helper scripts which are meant to be used for local development and CI environments:
/run-xxx.sh

Ruby source code is housed under /src/octo-fffc/lib/octo folder. There are several moving parts here:
* /fields - field type implementations for string, numeric and datetime fields
* /utils - helper classes, such as logging, optional and reflection helpers
* /services - read/write file services to support input file and CSV IO operation
* /clients - api/console clients which orchestrate file/row/column processing 
* /bin/octo-fffc - a ruby bin stub which is exposed by Ruby gem as a CLI on the target OS

All unit tests are located under /src/octo-fffc/spec/octo folder. Tehre is a "data" folder to store so-called "sets" of data - a number of folders with pre-defined file structure to support unit test and regression testing. Unit tests iuses only "01-simple" data, but Test Kitchen uses all of them.

### octo-fffc tool usage
Installation experience:
```
# Install from gem repository:
gem install octo-fffc --source "repository-url"

# local install from gem
gem install --local "path-to-gem-file"
```

Tool usage:
```
Usage: octo-fffc [options]
    -v, --[no-]verbose               run verbosely
    -i, --input-file=value           input file
    -d, --data-file=value            metadata file
    -o, --output-file=value          output file
    
octo-fffc -i /app/data.txt -d /app/metadata.csv -o /app/.output/output.csv
```

Docker container usage:
```
# map your data set into /app
# then pass octo-fffc with the right parameters
docker run --rm -it \
        -v $(pwd)/src/octo-fffc/spec/octo/data/sets/01-simple:/app \
        "octo-fffc:tool" \
        octo-fffc -i /app/data.txt -d /app/metadata.csv -o /app/.output/output.csv 
```

Output:
```
2018-12-09 22:00:04 +0000 INFO Starting octo-fffc util, client: console_client, v0.1.0
2018-12-09 22:00:04 +0000 INFO Reading metadata file: /app/metadata.csv
2018-12-09 22:00:04 +0000 INFO Processing input data file: /app/data.txt
2018-12-09 22:00:04 +0000 INFO Saved processed data to file: /app/.output/output.csv
2018-12-09 22:00:04 +0000 INFO Completed!
```

### TODO and tradeoffs
* add external CI server (TravisCI/AppVeyor)
* add more data sets into /src/octo-fffc/spec/octo/data/sets folders
* use randomized inout data for unit tests (with https://github.com/stympy/faker)
* add edge cases - special quotes, chars - for unit tests
* pre-generate random 1G, 2G, 3G and 4G input files to see if it still works
* add win8, win10 vagrant boxes under Test Kitchen
* default option values for octo-fffc CLI