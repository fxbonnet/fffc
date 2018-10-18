import unittest
import logging
import os
import tempfile
import shutil
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
        self.test_dir = tempfile.mkdtemp()
        self.tmp_output = os.path.join(self.test_dir, 'output')
        # Create temp meta file
        self.tmp_meta = os.path.join(self.test_dir, 'metadata')
        self.file_content = """Birth date,10,date
First name,15,string
Last name,15,string
Weight,5,numeric"""
        self._create_tmp_file(self.tmp_meta, self.file_content)

        # Create temp data file
        self.tmp_data = os.path.join(self.test_dir, 'data')
        self.file_content = """1970-01-01John           Smith           81.5
1975-01-31Jane           Doe             61.1
1988-11-28Bob            Big            102.4"""
        self._create_tmp_file(self.tmp_data, self.file_content)

        self.expected_headers = ['Birth date', 'First name', 'Last name', 'Weight']
        self.expected_parse_dates = ['Birth date']
        self.expected_dtypes = {'First name': 'object', 'Last name': 'object', 'Weight': 'float64'}
        self.expected_length_limit = {'Birth date': 10, 'First name': 15, 'Last name': 15, 'Weight': 5}
        self.expected_column_read_width = [10, 15, 15, 5]

    def tearDown(self):
        """ clean up tmp files after each test """
        shutil.rmtree(self.test_dir)

    @staticmethod
    def _create_tmp_file(file_name, content):
        with open(file_name, 'w') as file:
            file.write(content)

    def test_validate_metadata(self):
        fixed_file_format_converter = FixedFileFormatConverter(metadata=None, raw_data=None)
        row = {"column type": "string", "column length": 10, "Column name": "Test"}
        result = fixed_file_format_converter.validate_metadata(0, row)
        self.assertTrue(result)
        row = {"column type": "string", "column length": "False", "Column name": "Test"}
        result = fixed_file_format_converter.validate_metadata(0, row)
        self.assertFalse(result)
        row = {"column type": "False", "column length": 10, "Column name": "Test"}
        result = fixed_file_format_converter.validate_metadata(0, row)
        self.assertFalse(result)

    def test_load_metadata(self):
        fixed_file_format_converter = FixedFileFormatConverter(metadata=self.tmp_meta, raw_data="does not matter")
        fixed_file_format_converter.load_metadata()
        self.assertTrue(fixed_file_format_converter.headers == self.expected_headers)
        self.assertTrue(fixed_file_format_converter.parse_dates == self.expected_parse_dates)
        self.assertTrue(fixed_file_format_converter.column_read_width == self.expected_column_read_width)
        self.assertTrue(fixed_file_format_converter.length_limit == self.expected_length_limit)
        self.assertTrue(fixed_file_format_converter.dtypes == self.expected_dtypes)

    def test_validate_raw_data(self):
        fixed_file_format_converter = FixedFileFormatConverter(metadata=None, raw_data=None)
        fixed_file_format_converter.parse_dates = self.expected_parse_dates
        fixed_file_format_converter.headers = self.expected_headers
        fixed_file_format_converter.length_limit = self.expected_length_limit
        row = {"Birth date": "1986-08-21", "First name": "Brian", "Last name": "Huang", "Weight": 60.2}
        result = fixed_file_format_converter.validate_raw_data(0, row)
        self.assertTrue(result)
        row = {"Birth date": "1986-0a-21", "First name": "Brian", "Last name": "Huang", "Weight": 60.2}
        result = fixed_file_format_converter.validate_raw_data(0, row)
        self.assertFalse(result)
        row = {"Birth date": "1986-08-21", "First name": "Brian1222211111111", "Last name": "Huang", "Weight": 60.2}
        result = fixed_file_format_converter.validate_raw_data(0, row)
        self.assertFalse(result)

    def test_load_raw_data(self):
        fixed_file_format_converter = FixedFileFormatConverter(
            metadata=None,
            raw_data=self.tmp_data,
            output=self.tmp_output
        )
        fixed_file_format_converter.headers = self.expected_headers
        fixed_file_format_converter.parse_dates = self.expected_parse_dates
        fixed_file_format_converter.column_read_width = self.expected_column_read_width
        fixed_file_format_converter.length_limit = self.expected_length_limit
        fixed_file_format_converter.dtypes = self.expected_dtypes
        result = fixed_file_format_converter.load_raw_data()
        self.assertTrue( result == [self.tmp_output + "_1.csv"])
