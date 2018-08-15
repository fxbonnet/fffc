"""module containing formatting details for individual data types"""

from datetime import datetime
from typing import List

from data_types import DataTypes
from data_descriptor import DataDescriptor

_DATA_FILE_DATE_FORMAT = "%Y-%m-%d"
_CSV_DATE_FORMAT = "%d/%m/%Y"


def _format_date(data: str) -> str:
    """ instructions to format contents of type date

    :param data: string containing the date
    :return: string containing formatted date
    """
    return datetime.strptime(
        data, _DATA_FILE_DATE_FORMAT).strftime(_CSV_DATE_FORMAT)


def _format_string(data: str) -> str:
    """ instructions to format contents of type string

   :param data: string containing string data
   :return: string containing formatted string data
   """
    return data.rstrip()


def _format_numeric(data: str) -> str:
    """ instructions for formatting numeric data types

   :param data: string containing numeric data
   :return: string containing formatted numeric data
   """
    return data.strip().replace(',', '')


DEFAULT_MAPPER = {
    DataTypes.date: _format_date,
    DataTypes.string: _format_string,
    DataTypes.numeric: _format_numeric,
}


def format_row(data_row: str, data_descriptor: DataDescriptor,
               formatting_mapper: dict) -> List[str]:
    """format a row of data

    :param data_row: string containing data to be formatted
    :param data_descriptor: description of structure of a flat file
    :param formatting_mapper: mapping of data type and the formatter
    :return: a list containing formatted data
    """

    formatted_row = []
    for descriptor in data_descriptor.data_description:
        current_data = data_row[descriptor.start_index: descriptor.end_index]
        formatter = formatting_mapper[descriptor.data_type]
        formatted_row.append(formatter(current_data))

    return formatted_row
