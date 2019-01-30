# -*- coding: utf-8 -*-

import csv
import re
import datetime
import sys
import os


# configuration
delimiter = ','  # CSV delimiter
supported_types = (
    'string',
    'date',
    'numeric',
)


def load_metadata(filename):
    # init metadata
    metadata = {
        'line_length': 0,
        'columns': []
    }
    if not os.path.isfile(filename):
        return (None, "Metadata couldn't be found. Please, double check the data file has the metadata.")
    with open(filename, 'r', encoding="utf-8") as csv_file:
        reader = csv.reader(csv_file)
        for row in reader:
            if len(row) != 3:
                return (None, "Metadata file should have only 3 columns: column name, it's length and data type.")
            column_type = row[2]
            # parse int value with extra care
            length = 0  # ??
            try:
                length = int(row[1])
            except ValueError:
                return (None, "")

            name = row[0]
            
            if column_type not in supported_types:
                return (None, "Unsupported type was found in the metadata: " + column_type)
            column = {
                'name': row[0],  # length should be greated than 0?
                'length': int(row[1]),  # positive value?
                'type': column_type,
            }
            metadata['columns'].append(column)
    metadata['line_length'] = sum([int(val['length']) for val in metadata['columns']])
    return (metadata, None)

def parse_date(value):
    try:
        return (datetime.datetime.strptime(value.strip(), "%Y-%m-%d").strftime("%d/%m/%Y"), None)
    except ValueError:
        return (None, "Couldn't parse the date. Passed value: " + value)

def parse_string(value):
    return (value.strip(), None)

def parse_numeric(value):
    value = value.strip()
    res = re.findall('^(\-?\d+(?:\.\d+)?)$', value)
    if not res:
        return (None, "No numeric value can be found. Passed value: " + value)
    return (res[0], None)

def parse_raw_data(line, metadata):
    line = line.replace('\n', '').replace('\r', '')
    if len(line) != metadata['line_length']:
        return (None, "Length of the line is not according to the metadata.")
    parsed_values = []
    for column in metadata['columns']:
        length = column['length']
        column_type = column['type']
        value = line[:length]
        line = line[length:]
        res, err = globals()['parse_' + column_type](value)
        if err:
            return (None, err)
        parsed_values.append(res)
    return (tuple(parsed_values), None)

def print_console_header():
    print("fffc")
    print("----")
    print("Simple as 1, 2, 3. Specify the argument file with .data extension.")

def read_arguments():
    if len(sys.argv) != 2:
        return (None, None, None, "Wrong number of arguments were specified. Please, refer to the help.")
    filename = sys.argv[1]
    base = os.path.splitext(filename)[0]
    return (filename, base + '.meta', base + '.csv', None)

def convert_to_csv(input_data, metadata, output_csv):
    if not os.path.isfile(input_data):
        return (None, "Data file doesn't exist. Please, double check the correct path to the input data.")
    # Collect column names for CSV header from metadata
    headers = (column['name'] for column in metadata['columns'])
    # Save header to CSV file
    csv_file = open(output_csv, 'w', newline="\n", encoding="utf-8")
    csv_writer = csv.writer(csv_file, delimiter=delimiter)
    csv_writer.writerow(headers)
    csv_file.flush()
    with open(input_data, "r", encoding="utf-8") as text_file:
        line_number = 0
        for line in text_file:
            line_number += 1
            structured_data, err = parse_raw_data(line, metadata)
            if err:
                print("Error found in line " + str(line_number) + ": " + err)
                continue
            csv_writer.writerow(structured_data)
            csv_file.flush()
    csv_file.close()


if __name__ == "__main__":
    print_console_header()
    # read arguments from the command line. We expect only one argument
    input_data, input_metadata, output_csv, err = read_arguments()
    if err:
        print(err)
        exit(-1)
    # read input data and metadata from files
    metadata, err = load_metadata(input_metadata)
    if err:
        print(err)
        exit(-1)
    convert_to_csv(input_data, metadata, output_csv)
    print("Finished")