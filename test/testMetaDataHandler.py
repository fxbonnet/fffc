import unittest
from utils.metaDataHandler import MetaDataHandler
from utils.stringConverter import StringConverter


class TestMetaDataHandler(unittest.TestCase):
    def test_converter_factory(self):
        expected = StringConverter
        result = MetaDataHandler.converter_factory('string')

        self.assertIsInstance(result, expected)

    def test_converter_factory_(self):
        self.assertRaises(ValueError, MetaDataHandler.converter_factory, 'strange')


if __name__ == '__main__':
    unittest.main()
