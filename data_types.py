""" Module for all data types """

from enum import Enum


class DataTypes(Enum):
    """ Enum containing all permissible data types contained in a flat file """

    date = 1
    numeric = 2
    string = 3
