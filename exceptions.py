""" module containing custom exceptions """


class DataDescriptorParseError(Exception):
    """ Custom exception describing any error occurring while parsing
    descriptor file contents """

    def __init__(self, message):
        Exception.__init__(self, message)
