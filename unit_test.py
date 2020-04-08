from file_converter import *
import pytest
from command_line_parser import get_args


def test_convert_line_correct_line():
    """Convert a correct line, given the according metadata file."""
    line_to_parse = '1975-01-31Jane           Doe             61.1'
    columns_spec = parse_format('meta.txt')
    csv_line = convert_line(line_to_parse, columns_spec, 45)
    assert csv_line == ['31/01/1975', 'Jane', 'Doe', 61.1]


def test_convert_line_date_format_issue():
    """convert_line raises an ValueError exception if date is not in the right format"""
    line_to_parse = '195-01-310Jane           Doe             61.1'
    columns_spec = parse_format('meta.txt')
    with pytest.raises(ValueError):
        convert_line(line_to_parse, columns_spec, 45)


def test_convert_line_numeric_issue():
    """convert_line raises an ValueError exception if a numeric field is not in the right format"""
    line_to_parse = '1975-01-31Jane           Doe            61.1r'
    columns_spec = parse_format('meta.txt')
    with pytest.raises(ValueError):
        convert_line(line_to_parse, columns_spec, 45)


def test_convert_line_space_inside_field():
    """convert_line raises an ValueError exception if a space is found inside a field (after strip)"""
    line_to_parse = '1975-01-31Ja ne          Doe            61.1r'
    columns_spec = parse_format('meta.txt')
    with pytest.raises(Exception):
        convert_line(line_to_parse, columns_spec, 45)


def test_convert_line_line_length():
    """convert_line raises an ValueError exception if line length is not correct"""
    line_to_parse = '1975-01-31Jane            Doe            61.1r'
    columns_spec = parse_format('meta.txt')
    with pytest.raises(Exception):
        convert_line(line_to_parse, columns_spec, 45)


def test_parse_format_correct():
    """parse_format working well on a correct metadata file."""
    correct_repr_meta = "[Column(name='Birth date', size='10', type='date'), " \
                        "Column(name='First name', size='15', type='string'), " \
                        "Column(name='Last name', size='15', type='string'), " \
                        "Column(name='Weight', size='5', type='numeric')]"
    assert repr(parse_format('meta.txt')) == correct_repr_meta


def test_parse_format_incorrect():
    """parse_format raises an exception if the metadata file has a line containing more or less than 3 fields."""
    with pytest.raises(Exception):
        parse_format('wrong_meta.txt')


def test_global_correct():
    """convert_file works correctly on the example"""
    correct_output = 'Birth date,First name,Last name,Weight\n' \
                     '01/01/1997,John,Smith,81.5\n' \
                     '31/01/1975,Jane,Doe,61.1\n' \
                     '28/11/1988,Bob,Big,102.4\n'
    convert_file(path_input='input.txt', path_csv='output.txt', path_meta='meta.txt')
    computed_output = open('output.txt', 'r', encoding='utf8')
    assert computed_output.read() == correct_output
