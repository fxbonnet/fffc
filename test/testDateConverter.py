import unittest
from utils.dateConverter import DateConverter


class TestDateConverter(unittest.TestCase):

    def test_convertDate(self):
        expected = '01/01/1970'
        result = DateConverter.convert('1970-01-01')

        self.assertEqual(result, expected)

    def test_convertDate_wrong_format(self):
        self.assertRaises(ValueError, DateConverter.convert, '1970/01-01')


if __name__ == '__main__':
    unittest.main()
