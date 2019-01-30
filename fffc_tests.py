# -*- coding: utf-8 -*-

import fffc
import unittest


# https://docs.python.org/3.4/library/unittest.html

class ParseMethods(unittest.TestCase):
    def test_parse_numeric_successful(self):
        self.assertEqual(fffc.parse_numeric("5.00"), ('5.00', None))

    def test_parse_numeric_failed(self):
        self.assertEqual(fffc.parse_numeric("-5,00"), (None, "No numeric value can be found. Passed value: -5,00"))

    def test_parse_numeric_failed1(self):
        self.assertEqual(fffc.parse_numeric(""), (None, "No numeric value can be found. Passed value: "))

    def test_parse_date(self):
        self.assertEqual(fffc.parse_date("1985-06-08"), ("08/06/1985", None))

    def test_parse_date1(self):
        self.assertEqual(fffc.parse_date("1985-6-8"), ("08/06/1985", None))


if __name__ == '__main__':
    unittest.main()