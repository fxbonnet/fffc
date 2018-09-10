"""File containing functions that will process the input file"""
from datetime import datetime
from .exceptions import ParsingError


def process_numeric(text):
    """Rule to apply for numeric type"""
    try:
        if not text.strip():
            return None
        if "." in text:
            return float(text)
        return int(text)
    except Exception as exception:
        raise ParsingError(exception)


def process_date(text):
    """Rule to apply for date type"""
    try:
        if not text.strip():
            return None
        return datetime.strptime(text, '%Y-%m-%d').strftime('%d/%m/%Y')
    except Exception as exception:
        raise ParsingError(exception)


def process_string(text):
    """Rule to apply for string type"""
    try:
        return text.strip()
    except Exception as exception:
        raise ParsingError(exception)


def process_field(text, col_type):
    """Apply the correct rule depending on the input type"""
    return {
        "date": process_date,
        "numeric": process_numeric,
        "string": process_string
    }[col_type](text)


def process_line(line, meta_array, line_num):
    """Function that will process a line of string depending of the metadata information"""
    res = []
    for field in meta_array:
        if len(line) < field["col_length"]:
            raise ParsingError(
                "The line {} in the input file, does not contain "
                "enough characters to be processed.".format(line_num)
            )
        res.append(process_field(line[:field["col_length"]], field["col_type"]))
        line = line[field["col_length"]:]
    return res
