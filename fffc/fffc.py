import argparse
import csv
import logging
import pandas as pd
import numpy as np


class fffc(object):

    def __init__(self):
        self.args = None
        self.colnames = []
        self.colspec = []
        self.coltypes = []
        self.input = None
        self.level = None
        self.log = logging.getLogger(__name__)
        self.metaFile = None
        self.output = None

    def set_cli_args(self):
        # Arguments we support
        parser = argparse.ArgumentParser()
        parser.add_argument('-input', type=str, required=True,
                            help="The input file with the fixed format records you intend to process.")
        parser.add_argument('-level', type=str, default="INFO",
                            help="The required log level")
        parser.add_argument('-metadata', type=str, required=True,
                            help="The metadata file.")
        parser.add_argument('-output', type=str, required=True,
                            help="The output file that will contain the CSV data.")

        cmdargs = parser.parse_args()

        self.input = cmdargs.input
        self.level = cmdargs.level.upper()
        self.metaFile = cmdargs.metadata
        self.output = cmdargs.output

    def parsemeta(self):
        with open(self.metaFile) as metadata:
            metareader = csv.reader(metadata)

            index = 0
            for row in metareader:

                if len(row) != 3:
                    raise ValueError("A row definition must contain 3 elements. See line {} in the metadata file."
                                     .format(metareader.line_num))

                self.colnames.append(row[0])
                self.colspec.append((index, int(row[1])+index))
                if row[2] in ['date', 'numeric', 'string']:
                    self.coltypes.append(row[2])
                else:
                    raise ValueError("Unsupported type: {}. See line {} in the metadata file."
                                     .format(row[2], metareader.line_num))
                index += int(row[1])
            self.log.debug("Names: {}".format(self.colnames))
            self.log.debug("Spec: {}".format(self.colspec))
            self.log.debug("Types: {}".format(self.coltypes))

    def parsefixedfile(self):
        data = pd.read_fwf(self.input, colspecs=self.colspec, names=self.colnames)
        nulls = data[data.isnull().any(axis=1)]

        if len(nulls.index) > 0:
            raise ValueError("NULL values found in input data. Please check line {}".format(nulls.index[0]+1))

        indexes = [index for index in range(len(self.coltypes)) if self.coltypes[index] == 'date']
        if indexes is not None:
            for index in indexes:
                data[self.colnames[indexes[index]]] = pd.to_datetime(data[self.colnames[indexes[index]]],
                                                                     format="%d/%m/%Y", infer_datetime_format=True)
        data.to_csv(self.output, date_format="%d/%m/%Y", index=False, escapechar="\\",
                    sep=",", quoting=csv.QUOTE_MINIMAL, quotechar="\"",)

    def run(self):
        self.set_cli_args()
        logging.basicConfig(level=getattr(logging, self.level), format='%(asctime)-15s [%(levelname)s] %(message)s')
        self.parsemeta()
        self.parsefixedfile()


client = fffc()
