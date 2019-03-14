#!/usr/bin/python
# -*- coding: UTF-8 -*-

'''
Script to test converter basic functions
'''

import unittest
from converter import format_date
from converter import format_string
from converter import format_numeric
from exceptions import NumericFormatException
from exceptions import DateFormatException
from exceptions import StringFormatException
from exceptions import ParseMetaDataException

class TestConverter(unittest.TestCase):

    def test_format_date_with_correct_date(self):
        self.assertEqual(format_date("2013-12-11"), "11/12/2013")

    def test_format_date_with_correct_date_with_blank(self):
        self.assertEqual(format_date("2013-12-11 "), "11/12/2013")

    def test_format_date_with_wrong_format(self):
        self.assertRaises(DateFormatException, format_date, "2013/12/11")

    def test_format_date_with_wrong_year(self):
        self.assertRaises(DateFormatException, format_date, "-1-11-12")

    def test_format_date_with_wrong_month(self):
        self.assertRaises(DateFormatException, format_date, "2013-13-11")

    def test_format_date_with_wrong_date(self):
        self.assertRaises(DateFormatException, format_date, "2013-11-32")

    def test_format_numeric_with_correct_int(self):
        self.assertEqual(format_numeric("123"), "123")

    def test_format_numeric_with_correct_float(self):
        self.assertEqual(format_numeric("123.123"), "123.123")

    def test_format_numeric_with_correct_negative_int(self):
        self.assertEqual(format_numeric("-1"), "-1")

    def test_format_numeric_with_correct_negative_float(self):
        self.assertEqual(format_numeric("-1.123"), "-1.123")

    def test_format_numeric_with_correct_negative_float_zero(self):
        self.assertEqual(format_numeric("-1.0"), "-1.0")

    def test_format_numeric_with_left_blank(self):
        self.assertEqual(format_numeric(" -1.0"), "-1.0")

    def test_format_numeric_with_right_blank(self):
        self.assertEqual(format_numeric("-1.0 "), "-1.0")

    def test_format_numeric_with_special_character(self):
        self.assertRaises(NumericFormatException, format_numeric, "123,123")

    def test_format_numeric_with_internal_blank(self):
        self.assertRaises(NumericFormatException, format_numeric, "123 123")

    def test_format_string_with_correct_value(self):
        self.assertEqual(format_string("abc"), "abc")

    def test_format_string_with_special_characters(self):
        self.assertEqual(format_string("abc.:{}[]()*&^%$#@!~"), "abc.:{}[]()*&^%$#@!~")

    def test_format_string_with_comma1(self):
        self.assertEqual(format_string("abc,def"), "\"abc,def\"")

    def test_format_string_with_comma2(self):
        self.assertEqual(format_string(",abcdef"), "\",abcdef\"")

    def test_format_string_with_number_only(self):
        self.assertEqual(format_string("123456"), "123456")

    def test_format_string_with_internal_blank(self):
        self.assertEqual(format_string("123 456"), "123 456")

    def test_format_string_with_left_blank(self):
        self.assertEqual(format_string(" abc456"), "abc456")

    def test_format_string_with_right_blank(self):
        self.assertEqual(format_string("abc456 "), "abc456")


if __name__ == "__main__":
    suite = unittest.TestSuite()
    suite.addTest(unittest.makeSuite(TestConverter))
    unittest.main(defaultTest='suite')
