"""File for unit tests of metadata.py file"""
import pytest
from ..fffc.exceptions import ColumnFormatError
from ..fffc.metadata import create_meta_array


def test_that_a_right_metadata_file_is_correctly_processed():
    expected_output = [
        {"col_name": "Birth date", "col_length": 10, "col_type": "date"},
        {"col_name": "First name", "col_length": 15, "col_type": "string"},
        {"col_name": "Last name", "col_length": 15, "col_type": "string"},
        {"col_name": "Weight", "col_length": 5, "col_type": "numeric"}
    ]
    assert create_meta_array("./tests/metadata.csv") == expected_output


def test_that_a_metadata_file_containing_a_line_with_2_columns_return_a_column_format_exception():
    with pytest.raises(ColumnFormatError):
        create_meta_array("./tests/metadata_fail1.csv")


def test_that_a_metadata_file_containing_a_type_not_possible_return_a_column_format_exception():
    with pytest.raises(ColumnFormatError):
        create_meta_array("./tests/metadata_fail2.csv")
