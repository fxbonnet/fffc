
### Description
This tool need be executed by Python3.

Testing metadata and data files are stored in ./testcases directory in this repository.
CSV files will be generated in ./output directory in this repository. (errors outputted into CSV file directly.)

converter_test.py is used to run unit testing which should be invoked by travis automatically.

### Description for cases:
    case01: sunny-day case with negative number.
    case02: sunny-day case with chinese characters.
    case03: sunny-day case with ',' character.
    case04: sunny-day case with 4 columns.
    case05: rainy-day case with wrong length of one column
    case06: rainy-day case with wrong numeric type of one column
    case07: rainy-day case with wrong date type of one column
    case08: rainy-day case with missed column
    case09: rainy-day case with additional column
    case10: rainy-day case with wrong metadata type
    case11: rainy-day case with wrong numeric type
    case12: rainy-day case with wrong metadata type (float length)
    case13: rainy-day case with wrong month in data