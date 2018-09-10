"""File for unit tests of processor.py file"""
import pytest
from ..fffc.processor import *
from ..fffc.exceptions import ParsingError


def test_that_an_integer_processed_as_a_numeric_returns_an_integer():
    assert process_numeric("3456876") == 3456876


def test_that_an_integer_with_trailing_spaces_processed_as_a_numeric_returns_an_integer():
    assert process_numeric("    3456876    ") == 3456876


def test_that_a_float_processed_as_a_numeric_returns_a_float():
    assert process_numeric("3.14159265359") == 3.14159265359


def test_that_a_float_with_trailing_spaces_processed_as_a_numeric_returns_a_float():
    assert process_numeric("   3.14159265359   ") == 3.14159265359


def test_that_an_empty_string_processed_as_a_numeric_returns_nothing():
    assert process_numeric("") is None


def test_that_a_string_with_spaces_processed_as_a_numeric_returns_nothing():
    assert process_numeric("     ") is None


def test_that_a_character_string_processed_as_a_numeric_returns_a_parsing_error_exception():
    with pytest.raises(ParsingError):
        process_numeric("Unit")


def test_that_a_date_processed_returns_a_date_in_the_right_format():
    assert process_date("1987-08-23") == "23/08/1987"


def test_that_an_empty_string_processed_as_a_date_returns_nothing():
    assert process_date("") is None


def test_that_a_string_with_spaces_processed_as_a_date_returns_nothing():
    assert process_date("     ") is None


def test_that_a_character_string_processed_as_a_date_returns_a_parsing_error_exception():
    with pytest.raises(ParsingError):
        process_date("Unit")


def test_that_a_string_processed_returns_the_same_string():
    assert process_string("Success") == "Success"


def test_that_a_string_with_trailing_spaces_processed_returns_the_same_string_with_no_trailing_spaces():
    assert process_string("         Success        ") == "Success"


def test_that_a_field_processed_as_a_numeric_returns_a_numeric():
    assert process_field("2144645", "numeric") == 2144645


def test_that_a_field_processed_as_a_date_returns_a_date():
    assert process_field("1987-08-23", "date") == "23/08/1987"


def test_that_a_field_processed_as_a_string_returns_a_string():
    assert process_field("Success", "string") == "Success"


def test_that_a_line_of_text_is_properly_processed_depending_on_an_array_of_metadata():
    line = "1970-01-01John           Smith           81.5"
    meta_array = [
        {"col_name": "Birth date", "col_length": 10, "col_type": "date"},
        {"col_name": "First name", "col_length": 15, "col_type": "string"},
        {"col_name": "Last name", "col_length": 15, "col_type": "string"},
        {"col_name": "Weight", "col_length": 5, "col_type": "numeric"}
    ]
    assert process_line(line, meta_array, 1) == ["01/01/1970", "John", "Smith", 81.5]


def test_that_a_line_of_text_is_properly_processed_with_empty_values_depending_on_an_array_of_metadata():
    line = "          John           Smith           81.5"
    meta_array = [
        {"col_name": "Birth date", "col_length": 10, "col_type": "date"},
        {"col_name": "First name", "col_length": 15, "col_type": "string"},
        {"col_name": "Last name", "col_length": 15, "col_type": "string"},
        {"col_name": "Weight", "col_length": 5, "col_type": "numeric"}
    ]
    assert process_line(line, meta_array, 1) == [None, "John", "Smith", 81.5]


def test_that_a_short_line_of_text_processed_returns_a_parsing_exception():
    line = "1970-01-01John           Smith          "
    meta_array = [
        {"col_name": "Birth date", "col_length": 10, "col_type": "date"},
        {"col_name": "First name", "col_length": 15, "col_type": "string"},
        {"col_name": "Last name", "col_length": 15, "col_type": "string"},
        {"col_name": "Weight", "col_length": 5, "col_type": "numeric"}
    ]
    with pytest.raises(ParsingError):
        process_line(line, meta_array, 1)
