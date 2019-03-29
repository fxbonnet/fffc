import csv
import collections
from utils.dateConverter import DateConverter
from utils.numericConverter import NumericConverter
from utils.stringConverter import StringConverter


class MetaDataHandler():
    ColumnSpec = collections.namedtuple('ColumnSpec', 'name length typeConverter')

    def __init__(self, metadata_filename):
        self.filename = metadata_filename
        self.columns = []
        with open(metadata_filename, newline='') as metaDataCsv:
            reader = csv.reader(metaDataCsv)
            for row in reader:
                column = self.ColumnSpec(row[0], int(row[1]), self.converter_factory(row[2].lower()))
                self.columns.append(column)

    @staticmethod
    def converter_factory(col_type):
        if col_type == 'date':
            return DateConverter()
        elif col_type == 'numeric':
            return NumericConverter()
        elif col_type == 'string':
            return StringConverter()
        else:
            raise ValueError('Unknown format: %s' % col_type)
