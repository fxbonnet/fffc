""" Module for file converter tests """

import unittest
from os import path
import filecmp

from file_converter import convert_file


class FlatFileConverterIntegrationTests(unittest.TestCase):
    """ Integration tests for file conversion """

    def test_output_file_created(self):
        """ test for checking if output file is created"""

        convert_file("resources/metadata.csv", "resources/data_file",
                     "resources/data_file.csv")

        self.assertTrue(path.exists("resources/data_file.csv"))

    def test_output_file_contents(self):
        """ test for checking contents of output file """

        convert_file("resources/metadata.csv", "resources/data_file",
                     "resources/data_file.csv")

        self.assertTrue(filecmp.cmp("resources/data_file.csv",
                                    "resources/expected_data_file.csv",
                                    shallow=False))

    def test_file_not_created_no_metadata(self):
        """ test error is thrown when data descriptor file is not found"""

        with self.assertRaises(FileNotFoundError):
            convert_file("/some/location/metadata.csv", "resources/data_file",
                         "resources/data_file.csv")

    def test_file_not_created_no_datafile(self):
        """ test error is thrown when flat file is not found"""

        with self.assertRaises(FileNotFoundError):
            convert_file("resources/metadata.csv", "random/location/data_file",
                         "resources/data_file.csv")
