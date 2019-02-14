"""
Test fixed_file_converter Script
"""
import os
import unittest
import pandas as pd
from scripts import fixed_file_converter

TESTDATA_FILENAME = os.path.join(os.path.dirname(__file__), 'test.txt')
METADATA_FILENAME = os.path.join(os.path.dirname(__file__), 'test_meta.csv')
TESTOUTPUT_FILENAME = os.path.join(os.path.dirname(__file__),
                                   'test_output.csv')


class TestInputFiles(unittest.TestCase):
    """
    Test method to verify valid input files are provided
    """

    def setUp(self):
        self.testdata = open(TESTDATA_FILENAME, "r")
        self.metadata = pd.read_csv(METADATA_FILENAME,
                                    header=None,
                                    delimiter=",")
        self.output = pd.read_csv(TESTOUTPUT_FILENAME,
                                  delimiter=",")

    def test_no_input_files(self):
        """
        Test method creates error for non-existent input file
        """
        file_status = fixed_file_converter.file_exists(TESTDATA_FILENAME)
        self.assertTrue(file_status)

    def test_file_format(self):
        """
        Test method to check file format matches expected format
        """
        file_ext = fixed_file_converter.verify_file_type(TESTDATA_FILENAME,
                                                         ".txt")
        self.assertTrue(file_ext)

    def test_format_to_df(self):
        """
        Test method to ensure data frame is returned
        """
        csv_df = fixed_file_converter.txt_to_df(self.testdata,
                                                self.metadata[1],
                                                self.metadata[0])
        self.assertIsInstance(csv_df, pd.DataFrame)

    def test_convert_with_missing_input_file(self):
        """
        Test method to ensure fail if issue with input file
        """
        status = fixed_file_converter.convert(TESTDATA_FILENAME,
                                              "fake_file.cvs")
        self.assertEqual(status, 1)

    def test_convert_output(self):
        """
        Test method to ensure converted data frame is formatted as expected
        """
        csv_df = fixed_file_converter.convert(TESTDATA_FILENAME,
                                              METADATA_FILENAME)
        self.assertTrue(csv_df.equals(self.output))
