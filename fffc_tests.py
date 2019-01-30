import fffc
import unittest


class ParseMethods(unittest.TestCase):
    def test_parse_date(self):
        self.assertEqual(fffc.parse_date("1985-06-08"), "08/06/1985")


if __name__ == '__main__':
    unittest.main()