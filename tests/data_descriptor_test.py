""" Module containing tests for Data descriptor """

import unittest
import tempfile
import shutil
from os import path
import csv

from exceptions import DataDescriptorParseError
from data_descriptor import DataDescriptor


class DataDescriptorTest(unittest.TestCase):
    """ Tests for Data Description methods"""

    def setUp(self):
        self.test_dir = tempfile.mkdtemp()
        self.temp_csv_path = path.join(self.test_dir, 'data_descriptor.csv')

    def tearDown(self):
        shutil.rmtree(self.test_dir)

    def _setup_data_descriptor_file(self):
        """ Setup Data descriptor file"""

        with open(self.temp_csv_path, mode='w', newline='') as csv_file:
            writer = csv.writer(csv_file, delimiter=',', quotechar='"',
                                quoting=csv.QUOTE_MINIMAL)
            writer.writerow(["Birth date", "10", "date"])
            writer.writerow(["First name", "15", "string"])
            writer.writerow(["Last name", "15", "string"])

    def _setup_with_incorrect_columntype(self):
        """ Setup a data descriptor file with unexpected column type"""

        with open(self.temp_csv_path, mode='w', newline='') as csv_file:
            writer = csv.writer(csv_file, delimiter=',', quotechar='"',
                                quoting=csv.QUOTE_MINIMAL)
            writer.writerow(["Birth date", "10", "date1"])

    def _setup_with_empty_data_descriptor(self):
        """ Setup an empty data descriptor file """

        with open(self.temp_csv_path, 'w') as tmp_file:
            tmp_file.write("")

    def test_when_no_file(self):
        """ Test creation of data descriptor object when there is no
        DataDescriptor file present """

        with self.assertRaises(FileNotFoundError):
            DataDescriptor(self.temp_csv_path)

    def test_when_empty_file(self):
        """ Test creation of data descriptor object when the data
        DataDescriptor file is empty"""

        self._setup_with_empty_data_descriptor()
        with self.assertRaises(DataDescriptorParseError):
            DataDescriptor(self.temp_csv_path)

    def test_data_descriptor_headers(self):
        """ Test if the headers property is populated as expected """

        self._setup_data_descriptor_file()
        data_descriptor = DataDescriptor(self.temp_csv_path)
        self.assertEqual(data_descriptor.headers, ("Birth date", "First name",
                                                   "Last name"))

    def test_when_incorrect_column_type(self):
        """ Test creation of data descriptor object when the data
        DataDescriptor file has an incorrect cdata type """

        self._setup_with_incorrect_columntype()
        with self.assertRaises(DataDescriptorParseError):
            DataDescriptor(self.temp_csv_path)


if __name__ == '__main__':
    unittest.main()
