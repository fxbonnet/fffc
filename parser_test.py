from parser import slices, formatNumeric, formatDate, formatString, formatType, slices
import datetime
import unittest
import sys

class TestParsing(unittest.TestCase):

    def __init__(self, *args, **kwargs):
        super(TestParsing, self).__init__(*args, **kwargs)


    def testDate(self):
        self.assertEqual(formatDate("2001-12-12"), "12/12/2001")

    def testDateZeros(self):
        self.assertEqual(formatDate("2001-2-2"), "02/02/2001")

    def testFloat(self):
        self.assertEqual(formatNumeric("1."), 1.0)

    def testInt(self):
        self.assertEqual(formatNumeric("1"), 1)

    def testString(self):
        self.assertEqual(formatString("Chunkofstring"), "Chunkofstring")

    def testStringSpace(self):
        self.assertEqual(formatString("Chunk of string"), '\"Chunk of string\"')

    def testStringLeadSpace(self):
        self.assertEqual(formatString("   Chunk of string"), '\"   Chunk of string\"')

    def testStringComma(self):
        self.assertEqual(formatString("Chunk,string"), '\"Chunk,string\"')


    def testFormatUnknownType(self):
        self.assertRaises(Exception, formatType, "value", "Strong")

class TestSlicing(unittest.TestCase):

    def __init__(self, *args, **kwargs):
        super(TestSlicing, self).__init__(*args, **kwargs)
        self.specCharLine = "1970-01-01J©hn     ® ç  nSmith           81.5"

    def testSpecChars(self):
        self.assertListEqual(list(slices(self.specCharLine, [10, 15, 15, 5])),
                                ['1970-01-01', 'J©hn     ® ç  n', 'Smith          ', ' 81.5'])

    def testUnderSlice(self):
        self.assertListEqual(list(slices("1970-01-01J©hn     ® ç  nSmith           81.5", [10, 15, 15])),
                                ['1970-01-01', 'J©hn     ® ç  n', 'Smith          '])

    def testOverSlice(self):
        self.assertListEqual(list(slices(self.specCharLine, [10, 15, 15, 5, 5])),
                                ['1970-01-01', 'J©hn     ® ç  n', 'Smith          ', ' 81.5', ''])

if __name__ == '__main__':
    unittest.main()