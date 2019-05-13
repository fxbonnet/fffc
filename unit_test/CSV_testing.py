# Mohammad hamzehloui
# mohammad.hamzehloui@gmail.com

import unittest
from code import script as csv
import os


class Testing(unittest.TestCase):
    def test_create_csv(self):
        print("Basic:")
        print(
            "If everything goes right, you should be able to find the final csv file \n under "
            "the data folder with the name Results.csvThe format of the dates \n should"
            " be corrected, and all the spaces should be removed.\n\n")

        a = csv.CSV_Converter('../data/Metadata.csv', '../data/Data-Basic.txt', "../data/Basic.csv")
        a.main()
        self.assertTrue(os.path.isfile("../data/Basic.csv"))

    def test_date_format_error(self):
        print("Formatting error for dates:")
        print("Expecting ValueError on wrong entry for the dates. The program should exit\n "
              "and show the line number. The CSV file won't be created.(90 instead of 1970)\n\n")
        a = csv.CSV_Converter('../data/Metadata.csv', '../data/Data-Date-Format-Error.txt',
                              "../data/Date-format-Error.csv")
        with self.assertRaises(SystemExit):
            a.main()


    def test_number_format_error(self):
        print("\n\nFormatting error for numbers:")
        print("Expecting ValueError on wrong entry for the numbers. The program should \n "
              "exit and show the line number. The CSV file won't be created.\n\n")
        a = csv.CSV_Converter('../data/Metadata.csv', '../data/Data-Numbers-Format-Error.txt',
                              "../data/Numbers-Format-Error.csv")
        with self.assertRaises(SystemExit):
            a.main()

    def test_special_character(self):
        print("\n\nEscaping special characters")
        print("Should create the CSV file and escape the whole string with double quotation.")
        a = csv.CSV_Converter('../data/Metadata.csv', '../data/Data-Special-Character-escaped.txt',
                              "../data/Special-Character-escaped.csv")
        a.main()
        self.assertTrue(os.path.isfile("../data/Special-Character-escaped.csv"))


if __name__ == "__main__":
    unittest.main()
