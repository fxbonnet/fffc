# -*- coding: utf-8 -*-

import os
import hashlib
import unittest

import fffc


def get_file_checksum(filename):
    BLOCKSIZE = 65536
    hasher = hashlib.sha1()
    with open(filename, 'rb') as f:
        buf = f.read(BLOCKSIZE)
        while len(buf) > 0:
            hasher.update(buf)
            buf = f.read(BLOCKSIZE)
    return hasher.hexdigest()


class MainMethod(unittest.TestCase):
    def test(self):
        original_data_filename = "test_fixtures/original.data"
        original_metadata = "test_fixtures/original.meta"
        original_csv_filename = "test_fixtures/original.csv"
        os.remove(original_csv_filename) if os.path.isfile(original_csv_filename) else None
        metadata, err = fffc.load_metadata(original_metadata)
        if err:
            self.fail(err)
        fffc.convert_to_csv(original_data_filename, metadata, original_csv_filename)
        self.assertEqual(get_file_checksum(original_csv_filename), "6dfed0fb473d0dae1b005464da638e157169520c")

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