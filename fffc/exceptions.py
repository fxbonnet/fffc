"""Exceptions possible"""


class ColumnFormatError(Exception):
    """Basic exception for errors raised by wrong column format"""
    pass


class ParsingError(Exception):
    """Basic exception for errors raised by parsing error."""
    pass
