from datetime import datetime
from pathlib import Path
from collections import namedtuple
import csv

#  Constants
PATH_META = Path('./')
PATH_INPUT = Path('./')
NAME_META = 'meta.txt'
NAME_INPUT = 'input.txt'


def parse_format() -> list:
    """ Read meta file and return the input file format as a list of named tuple."""
    Column = namedtuple('Column', 'name size type')
    columns = []
    with open(PATH_META / NAME_META, 'r', encoding='utf8') as file:
        for line in file.read().splitlines():
            columns.append(Column(*line.split(',')))
    return columns


def convert_line(input_line: str, columns_spec) -> list:
    """Convert an input line to a list of string acording to the specified format"""

    line = []
    current_index = 0
    for col in columns_spec:
        el = input_line[current_index:current_index + int(col.size)].strip()
        if col.type == 'date':
            el = datetime.strptime(el, '%Y-%m-%d').strftime('%d/%m/%Y')
        line.append(el)
        current_index += int(col.size)

    return line


def convert_file(path_csv_file: str, name_csv_file: str):
    """ Reads the meta file and the INPUT file, and create a new csv file"""

    global PATH_INPUT, PATH_META, NAME_INPUT, NAME_META

    columns_spec = parse_format()

    with open(Path(path_csv_file) / name_csv_file, 'w+', encoding='utf8', newline='\n') as csv_file:
        writer = csv.writer(csv_file, delimiter=',')

        # Write header line
        writer.writerow(col.name for col in columns_spec)

        # write content lines
        with open(PATH_INPUT / NAME_INPUT, 'r', encoding='utf8') as file:
            for input_line in file.read().splitlines():
                csv_line = convert_line(input_line, columns_spec)
                writer.writerow(csv_line)


if __name__ == "__main__":
    path = ''
    name = 'output.csv'
    convert_file(path, name)
