import unittest

from formatters import format_row, DEFAULT_MAPPER
from data_descriptor import DataDescriptor


class FormattersTest(unittest.TestCase):

    def setUp(self):
        self.data_row = "1970-01-01John           Smith           81.5"

    def tearDown(self):
        pass

    def test_format_row_date(self):
        data_descriptor = DataDescriptor("resources/metadata.csv")
        formatted_row = format_row(self.data_row, data_descriptor,
                                   DEFAULT_MAPPER)
        self.assertEqual(formatted_row, ['01/01/1970', 'John', 'Smith', '81.5'])
