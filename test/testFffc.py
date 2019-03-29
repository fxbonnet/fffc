import unittest
from utils.metaDataHandler import MetaDataHandler
import fffc


class TestFFFC(unittest.TestCase):
    def test_parseARow(self):
        expected = ['01/01/1970', 'John', 'Smith', '81.5']
        mdh = MetaDataHandler('../input/metadata.csv')
        result = fffc.parse_a_row(mdh, '1970-01-01John           Smith           81.5')

        self.assertEqual(result, expected)

    def test_gen_header(self):
        mdh = MetaDataHandler('../input/metadata.csv')
        expected = '"Birth date","First name","Last name","Weight"\n'
        result = fffc.gen_header(mdh)

        self.assertEqual(result, expected)

    def test_total_length(self):
        mdh = MetaDataHandler('../input/metadata.csv')
        expected = 45
        result = fffc.total_length(mdh)

        self.assertEqual(result, expected)

    def test_parse_a_row(self):
        mdh = MetaDataHandler('../input/metadata.csv')
        expected = ['01/01/1970', 'John', 'Smith', '81.5']
        result = fffc.parse_a_row(mdh, '1970-01-01John           Smith           81.5')

        self.assertEqual(result, expected)

    def test_parse_a_row(self):
        mdh = MetaDataHandler('../input/metadata.csv')
        expected = ['01/01/1970', 'Jo",.-hn', 'Sm\"ith', '81.5']
        result = fffc.parse_a_row(mdh, '1970-01-01Jo",.-hn       Sm\"ith         81.5')

        self.assertEqual(expected, result)

    def test_parse_a_row_negative_value(self):
        mdh = MetaDataHandler('../input/metadata.csv')
        expected = ['01/01/1970', 'John', 'Smith', '-81.5']
        result = fffc.parse_a_row(mdh, '1970-01-01John           Smith          -81.5')

        self.assertEqual(result, expected)

    def test_parse_a_row_wrong_numer(self):
        mdh = MetaDataHandler('../input/metadata.csv')

        self.assertRaises(ValueError, fffc.parse_a_row, mdh, '1970-01-01John           Smith          8,1.5')

    def test_parse_a_row_wrong_date(self):
        mdh = MetaDataHandler('../input/metadata.csv')

        self.assertRaises(ValueError, fffc.parse_a_row, mdh, '1970$01-01John           Smith           81.5')

    def test_csv_formater(self):
        expected = '"aaa","b\"\"b","c\,c"\n'
        result = fffc.csv_formater(['aaa', 'b"b', 'c,c'])

        self.assertEqual(result, expected)

if __name__ == '__main__':
    unittest.main()
