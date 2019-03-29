import unittest
from utils.stringConverter import StringConverter


class TestStringConverter(unittest.TestCase):
    def test_convertDate(self):
        expected = 'qw,-"er'
        result = StringConverter.convert(' qw,-"er ')

        self.assertEqual(result, expected)


if __name__ == '__main__':
    unittest.main()
