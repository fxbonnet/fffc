"""Main module for the fixed format flat file converter"""

import logging
import argparse

from common_utils import timing
from file_io import read_file, write_csv
from data_descriptor import DataDescriptor
from formatters import format_row, DEFAULT_MAPPER

logging.basicConfig(level=logging.INFO)
LOGGER = logging.getLogger(__name__)


@timing
def convert_file(data_descriptor_file: str, data_file: str,
                 output_file: str) -> None:
    """ method to orchestrate the conversion of a flat file using a given data
    descriptor

    :param data_descriptor_file: the data descriptor for the flat file contents
    :param data_file: full path to the flat file to process
    :param output_file: full path to the expected output file
    """
    data_descriptor = DataDescriptor(data_descriptor_file)
    write_csv(output_file, data_descriptor.headers)

    for data_row in read_file(data_file):
        row = format_row(data_row, data_descriptor, DEFAULT_MAPPER)
        write_csv(output_file, row, append=True)


def parse_arguments():
    """ parses command line arguments """
    parser = argparse.ArgumentParser(description="Run flat file format "
                                                 "converter tool")
    parser.add_argument("metadata_file",
                        help="Full path of the metadata file describing the "
                             "flat file")
    parser.add_argument("data_file",
                        help="Full path of the flat file")
    parser.add_argument("output_file",
                        help="Full path of the output csv file")
    return parser.parse_args()


if __name__ == "__main__":
    ARGV = parse_arguments()
    convert_file(ARGV.metadata_file, ARGV.data_file, ARGV.output_file)
