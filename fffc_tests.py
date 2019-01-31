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


class FffcMethod(unittest.TestCase):
    def test(self):
        '''This unit test covers a success execution path of the application'''
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
        self.assertEqual(fffc.parse_numeric("5"), ('5', None))

    def test_parse_numeric_failed(self):
        self.assertEqual(fffc.parse_numeric("-5,00"), (None, "No numeric value can be found. Passed value: -5,00"))
        self.assertEqual(fffc.parse_numeric(""), (None, "No numeric value can be found. Passed value: "))

    def test_parse_date_successful(self):
        self.assertEqual(fffc.parse_date("1985-06-08"), ("08/06/1985", None))
        self.assertEqual(fffc.parse_date("1985-6-8"), ("08/06/1985", None))

    def test_parse_date_failed(self):
        self.assertEqual(fffc.parse_date("Wrong date"), (None, "Couldn't parse the date. Passed value: Wrong date"))
        self.assertEqual(fffc.parse_date("08-06-1985"), (None, "Couldn't parse the date. Passed value: 08-06-1985"))

class MetadataMethods(unittest.TestCase):
    def test_process_metadata_line_successful(self):
        self.assertEqual(fffc.process_metadata_line(["Column name", "5", "string"]), ({ 'name': 'Column name', 'length': 5, 'type': 'string'}, None))
        self.assertEqual(fffc.process_metadata_line(["Column name", "3", "numeric"]), ({ 'name': 'Column name', 'length': 3, 'type': 'numeric'}, None))
        self.assertEqual(fffc.process_metadata_line(["Column name", "10", "date"]), ({ 'name': 'Column name', 'length': 10, 'type': 'date'}, None))

    def test_process_metadata_line_failed(self):
        self.assertEqual(fffc.process_metadata_line(["Column name", "10", "date", "Additional column"]), (None, "Metadata file should have only 3 columns: column name, it's length and data type."))
        self.assertEqual(fffc.process_metadata_line(["", "10", "date"]), (None, "Column name cannot be empty in metadata file."))
        self.assertEqual(fffc.process_metadata_line(["Column name", "0", "numeric"]), (None, 'Length should be greater than 0 for Column name column'))
        self.assertEqual(fffc.process_metadata_line(["Column name", "5", "unsupported"]), (None, 'Unsupported unsupported type was found for Column name column'))
        self.assertEqual(fffc.process_metadata_line(["Column name", "f", "numeric"]), (None, 'Error happened while parsing length for Column name column.'))

    def test_load_metadata_successful(self):
        metadata, err = fffc.load_metadata("test_fixtures/original.meta")
        self.assertIsNone(err)
        self.assertEqual(metadata['line_length'], 45)

    def test_load_metadata_failed(self):
        metadata, err = fffc.load_metadata("test_fixtures/failed.meta")
        self.assertIsNotNone(err)
        self.assertEqual(err, "Unsupported wrongtype type was found for Last name column")
        metadata, err = fffc.load_metadata("test_fixtures/nosuchfile.meta")
        self.assertIsNotNone(err)
        self.assertEqual(err, "Metadata couldn't be found. Please, double check the data file has the metadata.")


if __name__ == '__main__':
    unittest.main()