# -*- coding: utf-8 -*-
"""
Created on Wed Apr  3 12:09:56 2019

@author: Vivek Arunagiri
Octo Task
"""
import os
import sys

'''
TEST_NO = '5'         # The folder path

DATA_FILE = 'Tests/'+ TEST_NO + '/data.txt'
METADATA_FILE = 'Tests/'+ TEST_NO + '/metadata.txt'
OUTPUT_FILE = 'Tests/'+ TEST_NO + '/output.txt'
'''

DATA_FILE = input('Path to input data file: ')
METADATA_FILE = input('Path to metadata file: ')
OUTPUT_FILE = input('Path to output file: ')
# remove output file if it exists
try:
    os.remove(OUTPUT_FILE)
except FileNotFoundError:
    pass

columns = []
with open(METADATA_FILE) as file:
    for line in file:
        if line[-1] == '\n':
            line = line[:-1]
        name, length, typ = line.split(',')
        columns.append([name,length,typ])

output = []
with open(DATA_FILE) as file:
    for line in file:
        if line[-1] == '\n':
            line = line[:-1]
        ind = 0
        row = []
        for n, l, t in columns:
            val = line[ind:ind+int(l)]
            ind += int(l)
            if t == 'date':
                if int(l) != 10:
                    print("DATE FORMAT INVALID\n")
                    sys.exit()
                year, month, day = val[:4], val[5:7], val[8:10]
                val = day + '/' + month + '/' + year
                try:
                    day = int(day)
                    month = int(month)
                    year = int(year)
                    if day > 31 or day < 1 or month < 1 or month > 12:
                        raise ValueError
                except ValueError:
                    print("DATE FORMAT INVALID\n")
                    sys.exit()
                
            elif t == 'string':
                val = val.strip()
                if '\\r' in val:
                    print("CR PRESENT IN STRING\n")
                    sys.exit()
                if '\\n' in val:
                    print("LF PRESENT IN STRING\n")
                    sys.exit()
            elif t == 'numeric':
                val = val.strip()
                val_list = list(val)
                if val_list.count('.') > 1:
                    print("NUMERIC FORMAT INVALID\n")
                    sys.exit()
                for i in val_list:
                    if i not in ['1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '.']:
                        print("NUMERIC FORMAT INVALID\n")
                        sys.exit()
            row.append(val)
        if ind != len(line):
            print("LINE LENGTH INVALID\n")
            sys.exit()
        output.append(row)


tot_count = len(output)
count = 0
headings = [e[0] for e in columns]
with open(OUTPUT_FILE, 'w') as file:
    head = ','.join(headings)
    print(head, file = file)
    for line in output:
        count += 1
        ans = ','.join(line)
        if count == tot_count:
            print(ans, file=file, end = '')
        else:
            print(ans, file=file)

print('OUTPUT file written')
