"""
Script to convert fixed file format files to a csv file based on a metadata
file describing its structure
"""

import os
import sys
import pandas as pd

COLNAMES = ["col_name", "col_len", "col_type"]


def file_exists(file_path):
    """
    Function to check input file path exists
    :param file_path: Path to input file
    :return: Bool
    """
    if os.path.isfile(file_path) is False:
        print("ERROR: Input file {} does not exist".format(file_path))
        return False
    return True


def verify_file_type(file_path, file_type):
    """
    Function to check file type matches expected
    :param file_path: Path to input file
    :param file_type: Expected type
    :return: Bool
    """
    if not file_path.endswith(file_type):
        print("ERROR: Expected {} input file instead got {}".format(
            file_type, file_path))
        return False
    return True


def verify_input_file(input_txt_path, input_csv_path):
    """
    Function to check file paths exist and that input files have expected
    format
    :param input_txt_path: Txt file path
    :param input_csv_path: CSV file path
    :return: Bool
    """
    if file_exists(input_txt_path) is False:
        return False
    elif file_exists(input_csv_path) is False:
        return False
    elif verify_file_type(input_txt_path, ".txt") is None:
        return False
    elif verify_file_type(input_csv_path, ".csv") is None:
        return False
    return True


def txt_to_df(file, col_len, col_names):
    """
    Function to convert txt file contents into data frame
    :param file: Txt file
    :param col_len: List of lengths for each column
    :param col_names: List of column names
    :return:
    """
    data = []
    for line in file.readlines():
        row = []
        for i in col_len:
            row.append(line[:i].strip())
            line = line[i:]
        data.append(row)

    return pd.DataFrame(data, columns=col_names)


def convert(txt_file_path, metadata_file_path, output_file_path="output.csv"):
    """
    Function to convert txt file
    :param txt_file_path: Path for txt file to be converted
    :param metadata_file_path: Path for associated csv file containing metadata
    :return: Output converted csv file
    """
    if verify_input_file(txt_file_path, metadata_file_path) is False:
        return 1

    txt_file = open(txt_file_path, "r")
    meta_data = pd.read_csv(metadata_file_path, header=None, delimiter=",")
    meta_data.columns = COLNAMES

    csv_df = txt_to_df(txt_file, meta_data.col_len, meta_data.col_name)

    for _, row in meta_data.iterrows():
        if row.col_type == "date":
            csv_df[row.col_name] = pd.to_datetime(csv_df[row.col_name])\
                .dt.strftime("%d/%m/%Y")
        elif row.col_type == "numeric":
            csv_df[row.col_name] = pd.to_numeric(csv_df[row.col_name])
    csv_df.to_csv(output_file_path, sep=",", encoding="utf-8", index=False)

    return csv_df


if __name__ == "__main__":

    convert(sys.argv[1], sys.argv[2])
