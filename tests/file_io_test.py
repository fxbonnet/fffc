import unittest
import shutil
import tempfile
from os import path
import csv

from file_io import read_file, read_csv, write_csv


class FileIoTest(unittest.TestCase):

    def setUp(self):
        self.test_dir = tempfile.mkdtemp()

        # Create temp file
        self.temp_file_path = path.join(self.test_dir, 'test.txt')
        self.file_content = 'The owls are not what they seem'
        self._create_temp_file(self.temp_file_path, self.file_content)

        # Create temp csv file
        self.temp_csv_path = path.join(self.test_dir, 'test.csv')
        self.csv_content = "header1,header2,header3,header4"
        self._create_temp_file(self.temp_csv_path, self.csv_content)
        self.expected_csv_content = ["header1", "header2", "header3", "header4"]

        # File name and content for csv write test
        self.write_csv_file_path = path.join(self.test_dir, 'test_data.csv')
        self.test_data_content = ["header1", "header2", "header3", "header4"]

    def tearDown(self):
        shutil.rmtree(self.test_dir)

    def _create_temp_file(self, file_name, content, is_csv=False):
        temp_file_path = path.join(self.test_dir, file_name)
        if is_csv:
            with open(temp_file_path, mode='w', newline='') as csv_file:
                writer = csv.writer(csv_file, delimiter=',', quotechar='"',
                                    quoting=csv.QUOTE_MINIMAL)
                writer.writerow(content)
        else:
            with open(temp_file_path, 'w') as tmp_file:
                tmp_file.write(content)

    def test_read_file_content(self):
        for line in read_file(self.temp_file_path):
            self.assertEqual(line, self.file_content)

    def test_read_csv_content(self):
        for line in read_csv(self.temp_csv_path):
            self.assertEqual(line, self.expected_csv_content)

    def test_write_csv_creates_file(self):
        write_csv(self.write_csv_file_path, self.test_data_content)
        self.assertTrue(path.exists(self.write_csv_file_path))

    def test_write_csv_content(self):
        write_csv(self.write_csv_file_path, self.test_data_content)
        with open(self.write_csv_file_path, 'r', encoding='utf-8',
                  newline='') as csv_file:
            reader = csv.reader(csv_file, delimiter=',', quotechar=',')
            for row in reader:
                self.assertEqual(row, self.expected_csv_content)


if __name__ == '__main__':
    unittest.main()
