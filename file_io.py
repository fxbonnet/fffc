""" module containing generator methods for reading/writing files and csv """

import csv
from typing import List


def read_file(file: str) -> str:
    """ read a file and yield lines

    :param file: the file to read from
    :return: row read from the file
    """
    with open(file, 'r', encoding='utf-8') as data:
        for line in data:
            yield line


def read_csv(file: str) -> str:
    """ read a csv file and yield lines

    :param file: the csv file to read from
    :return: single row read from the csv file
    """
    with open(file, 'r', encoding='utf-8', newline='') as csv_file:
        reader = csv.reader(csv_file, delimiter=',', quotechar=',')
        for row in reader:
            yield row


def write_csv(output_file: str, data: List[str], append: bool = False) -> None:
    """ write or append data into a csv file

    :param output_file: full path to the output csv file
    :param data: the content to write to the csv file
    :param append: flag indicating create a new file or append to existing file
    """
    mode = 'a' if append else 'w'
    with open(output_file, mode, newline='') as csv_file:
        writer = csv.writer(csv_file, delimiter=',', quotechar='"',
                            quoting=csv.QUOTE_MINIMAL)
        writer.writerow(data)
