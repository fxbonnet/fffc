#!/usr/bin/env python3

"""
Tests of converter.py
"""
import unittest
from converter import check_file, format_value, process_metadata

class TestConverter(unittest.TestCase):
    """Unit tests"""

    def test_input_file_exist(self):
        """Test the check file function with correct file"""
        self.assertTrue(check_file("files/metadata.csv"))

    def test_input_file_do_not_exist(self):
        """Test the check file function with incorrect file"""
        self.assertRaises(FileNotFoundError, check_file, "files/unknown.csv")

    def test_format_value_string_coma(self):
        """Test text values with coma"""
        self.assertEqual(format_value("mytext,coma", "string"), "mytext,coma")

    def test_format_value_string(self):
        """Test text values"""
        self.assertEqual(format_value("mytext", "string"), "mytext")

    def test_format_value_string_trailing_spaces(self):
        """Test text values with trailing spaces"""
        self.assertEqual(format_value("    mytext   ", "string"), "mytext")

    def test_format_value_numeric(self):
        """Test numeric values"""
        self.assertEqual(format_value("90", "numeric"), 90)

    def test_format_value_numeric_trailing_spaces(self):
        """Test numeric values with trailing spaces"""
        self.assertEqual(format_value("   90   ", "numeric"), 90)

    def test_format_value_negative_numeric(self):
        """Test negative numeric values"""
        self.assertEqual(format_value("-9", "numeric"), -9)

    def test_format_value_date(self):
        """Test negative numeric values"""
        self.assertEqual(format_value("1970-01-01", "date"), "01/01/1970")

    def test_format_value_date_trailing_spaces(self):
        """Test negative numeric values with trailing spaces"""
        self.assertEqual(format_value("  1970-01-01  ", "date"), "01/01/1970")

    def test_metadata_correct_format(self):
        """Test metadata with correct format"""
        metadata = [["Birth date", "10", "date"],
                    ["First name", "15", "string"],
                    ["Last name", "15", "string"],
                    ["Weight", "5", "numeric"]]

        result = (['Birth date', 'First name', 'Last name', 'Weight'],
                  [{'name': 'Birth date', 'length': 10, 'type': 'date'},
                   {'name': 'First name', 'length': 15, 'type': 'string'},
                   {'name': 'Last name', 'length': 15, 'type': 'string'},
                   {'name': 'Weight', 'length': 5, 'type': 'numeric'}])

        self.assertEqual(result, process_metadata(metadata))

    def test_metadata_incorrect_column_type(self):
        """Test metadata with incorrect column type"""
        metadata = [["Birth date", "10", "wrongtype"]]
        self.assertRaises(ValueError, process_metadata, metadata)

    def test_metadata_incorrect_column_number(self):
        """Test metadata with incorrect column number"""
        metadata = [["Birth date", "10", "string", "string"]]
        self.assertRaises(ValueError, process_metadata, metadata)

if __name__ == '__main__':
    unittest.main()
