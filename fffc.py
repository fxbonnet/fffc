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
        return (datetime.datetime.strptime(value, "%Y-%m-%d").strftime("%d/%m/%Y"), None)
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
    line = line.strip()
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

def save_csv(filename, data, metadata):
    for column in metadata['columns']:
        length = column['length']
    headers = (column['name'] for column in metadata['columns'])        
    with open(filename, 'w', newline="\n", encoding="utf-8") as f:
        writer = csv.writer(f, delimiter=delimiter)
        writer.writerow(headers)
        writer.writerows(data)

def load_data(filename):
    if not os.path.isfile(filename):
        return (None, "Data file doesn't exist. Please, double check the correct path to the input data.")
    text_file = open(filename, "r", encoding="utf-8")
    return (text_file.readlines(), None)

def print_console_header():
    print("fffc")
    print("Simple as 1, 2, 3. Specify the argument file with .data extension.")

def read_arguments():
    if len(sys.argv) != 2:
        return (None, None, None, "Wrong number of arguments were specified. Please, refer to the help.")
    filename = sys.argv[1]
    base = os.path.splitext(filename)[0]
    return (filename, base + '.meta', base + '.csv', None)


if __name__ == "__main__":
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
    raw_data, err = load_data(input_data)
    if err:
        print(err)
        exit(-1)
    # prepare an empty data list for processed data
    structured_data = []
    for index, line in enumerate(raw_data):
        res, err = parse_raw_data(line, metadata)
        if err:
            print("Error found in line " + str(index+1) + ": " + err)
            continue
        structured_data.append(res)
    if not structured_data:
        print("No CSV file was created because of found errors during the processing data.")
        exit(-1)
    # Save processed data to CSV file
    save_csv(output_csv, structured_data, metadata)
    print("Done")