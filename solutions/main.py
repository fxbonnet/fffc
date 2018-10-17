#!/usr/bin/env python3
from FixedFileFormatConverter import FixedFileFormatConverter
import logging
import os

LOG = logging.getLogger()
LOG.addHandler(logging.StreamHandler())


def main():
    fixed_file_format_converter = FixedFileFormatConverter()
    fixed_file_format_converter.load_metadata("metadata")
    fixed_file_format_converter.load_raw_data("data")


if __name__ == "__main__":
    try:
        if os.environ['DEBUG'] == "true":
            LOG.setLevel(logging.DEBUG)
    except KeyError:
        pass
    main()