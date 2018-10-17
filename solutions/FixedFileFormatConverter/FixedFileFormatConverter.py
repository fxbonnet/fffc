import pandas as pd
import multiprocessing as mp
import logging
import sys

# Logging program
LOG = logging.getLogger()


class FixedFileFormatConverter:
    legal_column_types = ["date", "numeric", "string"]
    headers = []
    parse_dates = []
    dtypes = {}
    length_limit = {}
    column_read_width = []
    output_filename = "output"
    output_count = 0
    chunk_size = 3  # Number of rows to read
    nup_of_process = 4

    def __init__(self):
        pass

    def validate_metadata(self, index, row):
        """
        validate metadata to see if it has illegal column types
        to see if column length is integer
        :param index:
        :param row:
        :return:
        """
        if row["column type"] not in self.legal_column_types:
            LOG.error("Invalid column type `{}` at line num {}".format(row["column type"], index+1))
            sys.exit(1)
        try:
            int(row["column length"])
        except ValueError:
            LOG.error("Invalid column length `{}` at line num {}. It should be integer.".format(row["column length"],
                                                                                                index+1))
            sys.exit(1)

    def load_metadata(self, filename):
        """
        read metadata file into memory
        extract all the date column
        extract all the columns" names
        :param filename:
        :return:
        """
        try:
            df = pd.read_csv(filename,
                             sep=",",
                             names=["column name", "column length", "column type"],
                             encoding="utf-8",
                             index_col=False
                             )
            LOG.debug("Head of Metadata:")
            LOG.debug(df.head())
            for index, row in df.iterrows():
                self.validate_metadata(index, row)
                if row["column type"] == "numeric":
                    column_type = "float64"
                    self.dtypes[row["column name"]] = column_type
                elif row["column type"] == "string":
                    column_type = "object"
                    self.dtypes[row["column name"]] = column_type
                self.length_limit[row["column name"]] = int(row["column length"])
                self.column_read_width.append(int(row["column length"]))
            self.headers = df[["column name"]]["column name"].tolist()
            parse_dates_df = df.loc[df["column type"] == "date"]
            self.parse_dates = parse_dates_df[["column name"]]["column name"].tolist()
        except Exception as e:
            LOG.error(e)
            sys.exit(1)

    def validate_raw_data(self, index, row):
        """
        validate raw data to see if valid date for relevant column
        to see if column length is within the limit
        :param index:
        :param row:
        :return:
        """
        for column in self.headers:
            if column in self.parse_dates:
                try:
                    pd.to_datetime(row[column])
                except Exception as e:
                    LOG.error("Error - Invalid Date format value: `{}` at line num {}".format(e.args[1], index+1))
                    sys.exit(1)
            elif len(str(row[column])) > self.length_limit[column]:
                LOG.error("Error - Invalid length of value: `{}` at line num {}. The length of column `{}` should be \
{} instead of {}".format(
                    row[column],
                    index + 1,
                    column,
                    self.length_limit[column],
                    len(str(row[column])))
                )
                sys.exit(1)

    def process_raw_data(self, chunk):
        """
        process the raw data
        output partition csv
        :param chunk:
        :return:
        """
        LOG.debug("Head of chunk:")
        LOG.debug(chunk.head())
        for index, row in chunk.iterrows():
            self.validate_raw_data(index, row)
        chunk.to_csv(self.output_filename+"_"+str(self.output_count)+".csv", date_format="%d/%m/%Y", index=False)

    def load_raw_data(self, filename):
        """
        read data file into memory
        convert date to dd/mm/yyyy format
        output a csv file
        :param filename:
        :return:
        """
        try:
            reader = pd.read_fwf(filename,
                                 names=self.headers,
                                 widths=self.column_read_width,
                                 parse_dates=self.parse_dates,
                                 encoding="utf-8",
                                 chunksize=self.chunk_size,
                                 )
            pool = mp.Pool(self.nup_of_process)
            for chunk in reader:
                self.output_count += 1
                pool.apply_async(self.process_raw_data, [chunk])
        except Exception as e:
            LOG.error("Errors while reading file: `{}`".format(filename))
            LOG.error(e)
            sys.exit(1)
