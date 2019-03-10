#!/usr/bin/env python3

"""
Generic tool to convert fixed file format files to a csv file based on a
metadata file describing its structure.
"""

import os
import csv
import errno
import argparse
from datetime import datetime
import time
import logging

# Configuration
DELIMITER = ','
QUOTE = '"'
ALLOWED_COLUMNS_TYPES = ['date', 'string', 'numeric']
logging.basicConfig(format='%(asctime)s - %(message)s', level=logging.INFO)

def format_value(text, line_type):
    """Format line according to requirements"""
    if line_type == "string":
        formatted_line = text.strip()
    if line_type == "numeric":
        if "." in text:
            formatted_line = float(text)
        else:
            formatted_line = int(text)
    if line_type == "date":
        value = text.strip()
        formatted_line = datetime.strptime(value, '%Y-%m-%d').strftime(
            '%d/%m/%Y')
    return formatted_line

def process_rawdata(header, metadata, rawdata, output):
    """Check and process raw data"""
    csv_writer = csv.writer(output, delimiter=DELIMITER, quotechar=QUOTE)
    csv_writer.writerow(header) # Write header line

    # Go through all the raw data content
    for line, row in enumerate(rawdata, 1):
        processed_line = list()
        for meta in metadata:
            if len(row) < meta["length"]:
                raise ValueError(
                    "Invalid length in line: {}".format(line)
                )
            value = format_value(row[:meta["length"]], meta["type"])
            processed_line.append(value)
            row = row[meta["length"]:]
        csv_writer.writerow(processed_line)
    return True

def process_metadata(metadata):
    """Check metadata and extract header"""
    header = list()
    metadata_structure = list()
    for line, row in enumerate(metadata):
        if row[2] not in ALLOWED_COLUMNS_TYPES:
            raise ValueError('Invalid column type in line: {}'.format(line))
        if len(row) != 3:
            raise ValueError('You should provide exactly 3 columns \
                             in line: {}'.format(line))
        header.append(row[0])
        metadata_structure.append({
            "name": row[0],
            "length": int(row[1]),
            "type": row[2]
        })
    return (header, metadata_structure)

def check_file(file_path, file_create=False):
    """Check if file exists, creates the file if requested"""
    if os.path.isfile(file_path) is False:
        if file_create is True:
            open(file_path, mode='w', encoding="UTF-8").close()
        else:
            raise FileNotFoundError(
                errno.ENOENT, os.strerror(errno.ENOENT), file_path)
    return True

if __name__ == "__main__":
    PARSER = argparse.ArgumentParser(description='Generic tool to convert \
                                     fixed file format files to a csv file \
                                     based on a metadata file describing its \
                                     structure.')
    PARSER.add_argument('-d', metavar='data', dest='data', required=True,
                        help='raw data file path')
    PARSER.add_argument('-m', metavar='metadata', dest='metadata',
                        required=True, help='metadata csv file path')
    PARSER.add_argument('-o', metavar='output', dest='output',
                        required=False, help='output csv file path')

    ARGS = PARSER.parse_args()
    START_TIME = time.time()
    logging.info("Converter started")

    if check_file(ARGS.data):
        DATA_FILE = open(ARGS.data, mode='r', encoding="UTF-8")

    if check_file(ARGS.metadata):
        META_FILE = open(ARGS.metadata, mode='r', encoding="UTF-8")
        META_READER = csv.reader(META_FILE, delimiter=DELIMITER)
        METADATA = process_metadata(META_READER)

    if check_file(ARGS.output, True):
        OUTPUT_FILE = open(ARGS.output, mode='w', encoding="UTF-8")

    if process_rawdata(METADATA[0], METADATA[1], DATA_FILE, OUTPUT_FILE):
        END_TIME = time.time()-START_TIME
        DATA_FILE.close()
        META_FILE.close()
        OUTPUT_FILE.close()
        logging.info("File converted successfully in %fs", END_TIME)
