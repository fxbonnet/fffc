#!/usr/bin/env python3
from converter import FixedFileFormatConverter
import logging
import os
import sys
import argparse

LOG = logging.getLogger()
LOG.addHandler(logging.StreamHandler())


def get_args():
    parser = argparse.ArgumentParser(description='Fixed Format File Converter')

    parser.add_argument('--metadata',
                        required=True,
                        help='The metadata file describing the structure')

    parser.add_argument('--data',
                        required=True,
                        help='The fixed format raw data file')

    parser.add_argument('--output',
                        default='output',
                        required=False,
                        help='The output file name prefix')

    return parser.parse_args()


def main():
    args = get_args()
    metadata = args.metadata
    data = args.data
    output = args.output
    fixed_file_format_converter = FixedFileFormatConverter(metadata=metadata, raw_data=data, output=output)
    if not fixed_file_format_converter.load_metadata():
        sys.exit(1)

    result = fixed_file_format_converter.load_raw_data()
    print("Please see the following output files: "+";".join(result))


if __name__ == "__main__":
    try:
        if os.environ['DEBUG'] == "true":
            LOG.setLevel(logging.DEBUG)
    except KeyError:
        pass
    main()
