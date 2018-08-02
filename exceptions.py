""" module containing custom exceptions """


class DataDescriptorParseError(Exception):
    """ Custom exception describing any error occurring while parsing
    descriptor file contents """

    def __init__(self, message):
        Exception.__init__(self, message)


class FlatFileParseError(Exception):
    """ Custom exception describing any error occurring while parsing
    flat file contents """

    def __init__(self):
        Exception.__init__(self, "Error while parsing data descriptor")
