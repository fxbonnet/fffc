import unittest
from utils.numericConverter import NumericConverter


class TestNumericConverter(unittest.TestCase):
    def test_convertNumeric(self):
        expected = '1970.8'
        result = NumericConverter.convert('1970.8')

        self.assertEqual(result, expected)

    def test_convertNumeric_with_separator(self):
        self.assertRaises(ValueError, NumericConverter.convert, '1,970.8')

    def test_convertNumeric_negative(self):
        expected = '-1970.8'
        result = NumericConverter.convert('-1970.8')

        self.assertEqual(result, expected)


if __name__ == '__main__':
    unittest.main()
