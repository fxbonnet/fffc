[![CircleCI](https://circleci.com/gh/brianhhq/fffc.svg?style=svg&circle-token=54fea42b442b3eade1ecddea434cbb6c19535a84)](https://circleci.com/gh/brianhhq/fffc)
#  Fixed File Format converter Solution

A generic tool to convert fixed file format files to a csv file based on a metadata file describing its structure.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. 

### Prerequisites

Donwload and install [Docker](https://www.docker.com/get-started)
What things you need to install the software and how to install them


### Installing

Download the images via command line

```
docker login
docker pull brianhhq/fffc
```

### Usage

```bash
sudo docker run -d -t --name converter brianhhq/fffc sh
sudo docker exec -t converter python main.py -h
sudo docker exec -t converter python main.py --metadata metadata --data data
sudo docker exec -t converter cat output_1.csv
```

#### Run Program with default data
```bash
sudo docker exec -t converter python main.py --metadata metadata --data data
sudo docker exec -t converter cat output_1.csv
```

#### RUN Program with your test data
copy file from host to container
```bash
sudo docker cp <host_file_name> converter:/app
sudo docker exec -t converter python main.py --metadata metadata --data data
sudo docker exec -t converter cat output_1.csv
```


## Running the tests

```
sudo docker exec -t converter python -m unittest tests.FixedFileFormatConverterTestCase
```


## Built With

* [pandas](https://pandas.pydata.org) - an open source, BSD-licensed library providing high-performance, easy-to-use data structures and data analysis tools


## Contributing

Please submit pull requests to us.


## Authors

* **Brian Huang**
* brian_hhq@hotmail.com


## License

This project is licensed under the MIT License.
