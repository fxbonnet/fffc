#!/usr/bin/python
# -*- coding: UTF-8 -*-

'''
Script to define exceptions
'''

class NumericFormatException(Exception):
    '''
    Custom NumericFormatException type
    '''
    def __init__(self, parse_value):
        err = 'Failed to formate numeric "{0}"'.format(parse_value)
        Exception.__init__(self, err)
        self.parse_value = parse_value


class DateFormatException(Exception):
    '''
    Custom DateFormatException type
    '''
    def __init__(self, parse_value):
        err = 'Failed to formate date "{0}"'.format(parse_value)
        Exception.__init__(self, err)
        self.parse_value = parse_value


class StringFormatException(Exception):
    '''
    Custom StringFormatException type
    '''
    def __init__(self, parse_value):
        err = 'Failed to formate string "{0}"'.format(parse_value)
        Exception.__init__(self, err)
        self.parse_value = parse_value


class ParseMetaDataException(Exception):
    '''
    Custom ParseMetaDataException type
    '''
    def __init__(self, parse_value):
        err = 'Failed to parse metadata "{0}"'.format(parse_value)
        Exception.__init__(self, err)
        self.parse_value = parse_value