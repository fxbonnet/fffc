from datetime import datetime
from pathlib import Path
from collections import namedtuple
from command_line_parser import get_args
import csv


def parse_format(path_meta: 'Path object') -> list:
    """ Read meta file and return the input file format as a list of named tuple."""
    Column = namedtuple('Column', 'name size type')
    err_msg_format = 'Meta File Format Error : Error in Line \n{}\nA line should contain 3 fields, {} were given.'
    columns = []
    with open(path_meta, 'r', encoding='utf8') as file:
        for line in file:
            if line[-1] == '\n':
                line = line[:-1:]
            cols = line.split(',')
            if len(cols) != 3:
                raise Exception(err_msg_format.format(line, len(cols)))
            columns.append(Column(*cols))
    return columns


def convert_line(input_line: str, columns_spec: list, correct_line_length: int) -> list:
    """Convert an input line to a list of string according to the specified format."""
    head_msg = '\nInput File Format Error :'

    if len(input_line) != correct_line_length:
        err_msg_length = 'Line \n{}\nis not the correct length : {} instead of {}'
        raise Exception(head_msg + err_msg_length.format(input_line, len(input_line), correct_line_length))

    line = []
    current_index = 0
    for col in columns_spec:
        el = input_line[current_index:current_index + int(col.size)].strip()

        if ' ' in el:
            err_msg_space = 'Spaces are not allowed in fields, space found in element "{}" in line \n{}'
            raise Exception(head_msg + err_msg_space.format(el, input_line))

        if col.type == 'date':
            try:
                el = datetime.strptime(el, '%Y-%m-%d').strftime('%d/%m/%Y')
            except ValueError:
                err_msg_date = '\nDate field "{}" of line \n{}\nis not in the right format : aaaa-mm-dd'
                raise ValueError(head_msg + err_msg_date.format(el, input_line))

        if col.type == 'numeric':
            try:
                el = float(el)
            except ValueError:
                err_msg_numeric = '\nNumeric field "{}" of line \n{}\n is not numeric'
                raise ValueError(head_msg + err_msg_numeric.format(el, input_line))

        line.append(el)
        current_index += int(col.size)
    return line


def convert_file(path_input: str, path_meta: str, path_csv: str):
    """ Reads the meta file and the INPUT file, and creates a new csv file."""
    path_input, path_meta, path_output = Path(path_input), Path(path_meta), Path(path_csv)
    columns_spec = parse_format(path_meta)  # Gets the format from the metadata file
    correct_line_length = sum(int(col.size) for col in columns_spec)

    with open(path_csv, 'w+', encoding='utf8', newline='\n') as csv_file:
        writer = csv.writer(csv_file, delimiter=',')

        # Write header line
        writer.writerow(col.name for col in columns_spec)

        # write content lines
        with open(path_input, 'r', encoding='utf8') as file:
            for input_line in file:
                if input_line[-1] == '\n':
                    input_line = input_line[:-1:]
                csv_line = convert_line(input_line, columns_spec, correct_line_length)
                writer.writerow(csv_line)


if __name__ == "__main__":
    args = get_args()
    convert_file(args.input_path, args.meta_path, args.output_path)
