#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""Main Application File"""
import os
import csv
import argparse
from fffc.metadata import create_meta_array
from fffc.processor import process_line


if __name__ == "__main__":
    PARSER = argparse.ArgumentParser(description="Please provide Metadata as -m,"
                                                 " InputFile as -i and OutputFile as -o")
    PARSER.add_argument("-m",
                        dest="Metadata",
                        required=True,
                        help="Provide the path to the Metadata file.")
    PARSER.add_argument("-i",
                        dest="InputFile",
                        required=True,
                        help="Provide the path to the Input file.")
    PARSER.add_argument("-o",
                        dest="OutputFile",
                        required=False,
                        help="Provide the path to the Output file.")
    ARGS = PARSER.parse_args()

    if not ARGS.OutputFile:
        ARGS.OutputFile = os.path.dirname(ARGS.InputFile) + "/processed_file.csv"

    META_ARRAY = create_meta_array(ARGS.Metadata)
    HEADER = [field["col_name"] for field in META_ARRAY]

    with open(ARGS.InputFile, mode='r', encoding="utf-8", errors="strict") as input_file, \
            open(ARGS.OutputFile, mode='w', encoding="utf-8", newline='', errors="strict") as output_file:
        CSV_WRITTER = csv.writer(output_file, delimiter=',', quotechar='"')
        CSV_WRITTER.writerow(HEADER)
        for line_num, line in enumerate(input_file, 1):
            CSV_WRITTER.writerow(process_line(line, META_ARRAY, line_num))
