""" Module containing classes for description of data in a flat file """

from dataclasses import dataclass

from data_types import DataTypes
from file_io import read_csv
from exceptions import DataDescriptorParseError


@dataclass
class RowDescription:
    """ Description for each column and its char length in a flat file """

    field_name: str
    start_index: int
    end_index: int
    data_type: DataTypes


def _get_row_description(file_name: str):
    """ Generator providing description for each row

    :param file_name: complete path of file containing the description of the
    contents of a flat file
    :returns: a RowDescription object containing row description
    """

    start_index = 0
    for field_name, char_length, data_type in read_csv(file_name):

        if data_type not in DataTypes.__members__:
            raise DataDescriptorParseError(f"Unexpected datatype {data_type}")

        char_length = int(char_length)
        yield RowDescription(field_name,
                             start_index,
                             start_index + char_length,
                             DataTypes[data_type])
        start_index += char_length


class DataDescriptor:
    """Data descriptor for a flat file """

    def __init__(self, file_name: str):
        self.data_description = tuple(row_description for row_description
                                      in _get_row_description(file_name))

        if not self.data_description:
            raise DataDescriptorParseError("Data descriptor file was empty")

        self.data_headers = tuple(row_description.field_name for
                                  row_description in self.data_description)

    @property
    def headers(self) -> tuple:
        """ Get column names described in a data description file"""
        return self.data_headers
