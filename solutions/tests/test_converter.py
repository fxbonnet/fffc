import unittest
import logging
import os
from converter import FixedFileFormatConverter

LOG = logging.getLogger()
LOG.addHandler(logging.StreamHandler())


class FixedFileFormatConverterTestCase(unittest.TestCase):

    def setUp(self):
        try:
            if os.environ['DEBUG'] == "true":
                LOG.setLevel(logging.DEBUG)
        except KeyError:
            pass

    def test_load_metadata(self):
        fixed_file_format_converter = FixedFileFormatConverter()
        fixed_file_format_converter.load_metadata("metadata")
        self.assertTrue(1 == 1)
