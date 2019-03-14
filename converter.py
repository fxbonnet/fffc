#!/usr/bin/python
# -*- coding: UTF-8 -*-

'''
Script to convert to fixed file format to csv
'''

import os
import sys
import getopt
from datetime import datetime
from exceptions import NumericFormatException
from exceptions import DateFormatException
from exceptions import StringFormatException
from exceptions import ParseMetaDataException
from meta_type import DataTypes

def format_date(value):
    '''
    Function to formate date type
    '''
    try:
        return datetime.strptime(value.strip(), '%Y-%m-%d').strftime('%d/%m/%Y')
    except:
        raise DateFormatException(value.strip())


def format_string(value):
    '''
    Function to formate string type
    '''
    try:
        if value.find(',') > -1:
            return '\"%s\"' %(value.strip())
        else:
            return value.strip()
    except:
        raise StringFormatException(value.strip())


def format_numeric(value):
    '''
    Function to formate numeric type
    '''
    try:
        float(value.strip())
        return str(value.strip())
    except:
        raise NumericFormatException(value.strip())


def parse_metadata(metadata_file):
    '''
    Function to parse metadata and generate metadata_structure
    '''
    metadata_structure = []
    with open(metadata_file, 'r', encoding='utf-8') as f:
        for line in f:
            line = line.replace("\n", "")
            metadata = {}
            metadata['name'] = line.split(',')[0].strip()
            metadata['length'] = line.split(',')[1].strip()
            metadata['type'] = line.split(',')[2].strip()
            if len(metadata['name']) == 0:
                raise ParseMetaDataException(metadata['name'])

            try:
                int(metadata['length'])
            except:
                raise ParseMetaDataException(metadata['length'])

            if metadata['type'].lower() not in DataTypes:
                raise ParseMetaDataException(metadata['type'])

            metadata_structure.append(metadata)
    return metadata_structure


def parse_line(metadata_structure, line):
    '''
    Parse each line from fix format file
    '''
    if (metadata_structure is None) or (len(metadata_structure) == 0):
        raise ParseMetaDataException("parse metadata failure")

    line = line.replace("\n", "")
    columns = []
    parse_idx = 0
    for item in metadata_structure:
        length = int(item['length'])
        data_type = item['type']
        column = line[parse_idx:parse_idx+length]
        if data_type.lower() == 'date':
            column=format_date(column).strip()
        elif data_type.lower() == 'numeric':
            column=format_numeric(column).strip()
        elif data_type.lower() == 'string':
             column=format_string(column).strip()

        columns.append(column)
        parse_idx = parse_idx + length

    return ','.join(columns)


def convert_to_csv(metadata_file, data_file, output_file):
    '''
    Generate csv file.
    '''
    case_name = data_file.split('/')[-2]
    with open(output_file, 'w', encoding='utf-8') as wf, open(data_file, 'r', encoding='utf-8') as rf:
        last_line = ''
        try:
            metadata_structure = parse_metadata(metadata_file)
            heads = []
            for item in metadata_structure:
                heads.append(item['name'].strip())
            wf.write(','.join(heads) + "\n")

            for line in rf:
                last_line = line.replace("\n", "")
                csv_line = parse_line(metadata_structure, line)
                wf.write(csv_line + "\n")

            print("Generate csv for %s successfully." %(case_name))
        except NumericFormatException as e1:
            print("Generate csv for %s with failure." %(case_name))
            wf.write("Failed to parse numeric for line(%s). error:%s" %(last_line, e1))
        except DateFormatException as e2:
            print("Generate csv for %s with failure." %(case_name))
            wf.write("Failed to parse date for line(%s). error:%s" %(last_line, e2))
        except StringFormatException as e3:
            print("Generate csv for %s with failure." %(case_name))
            wf.write("Failed to parse string for line($s). error:%s" %(last_line, e3))
        except ParseMetaDataException as e4:
            print("Generate csv for %s with failure." %(case_name))
            wf.write("Failed to parse metadata. error:%s" %(e4))
        except Exception as exception:
            print("Generate csv for %s with failure." %(case_name))
            wf.write("Error while parsing line(%s). error:%s" %(last_line, exception))


if __name__ == "__main__":
    metadata_file = None
    data_file = None
    output_file = None
    opts, args = getopt.getopt(sys.argv[1:], "m:d:o:")
    for op, value in opts:
        if op == "-m":
            metadata_file = value
        elif op == "-d":
            data_file = value
        elif op == "-o":
            output_file = value

    if metadata_file is not None and data_file is not None and output_file is not None:
        try:
            convert_to_csv(metadata_file, data_file, output_file)
        except:
            print("Exception while generating csv.")
    else:
        print("Metadata/Data/Output files missed.")