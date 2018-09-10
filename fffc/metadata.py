"""File containing function that will handle metadata file"""
import csv
from .exceptions import ColumnFormatError


def create_meta_array(meta_path):
    """This function takes the path of the metadata file and returns a list of dict"""
    schema = []
    with open(meta_path, mode='r', encoding="UTF-8", errors="strict") as meta_file:
        meta_reader = csv.reader(meta_file, delimiter=',')
        for row in meta_reader:
            if len(row) != 3:
                raise ColumnFormatError(
                    "Error in line {} of metadata file, there should be only 3 elements "
                    "but {} were found.".format(meta_reader.line_num, len(row))
                )
            if int(row[1]) <= 1:
                raise ColumnFormatError(
                    "Error in line {} of metadata file, "
                    "the length of a column must be superior to 1.".format(meta_reader.line_num)
                )
            if row[2] not in {"numeric", "string", "date"}:
                raise ColumnFormatError(
                    "Error in line {} of metadata file, the type of column "
                    "must be one of numeric, string or date.".format(meta_reader.line_num)
                )
            if (row[2] == "date") and (int(row[1]) != 10):
                raise ColumnFormatError(
                    "Error in line {} of metadata file, the type of column is date, "
                    "but the length is not equal to 10.".format(meta_reader.line_num)
                )
            schema.append({
                "col_name": row[0],
                "col_length": int(row[1]),
                "col_type": row[2]
            })
    return schema
