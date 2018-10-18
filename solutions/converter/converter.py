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
    default_output_filename = "output"
    output_count = 0
    chunk_size = 10**6  # Number of rows to read
    num_of_process = 4

    def __init__(self, metadata, raw_data, output=None):
        if output is None:
            self.output_filename = self.default_output_filename
        else:
            self.output_filename = output
        self.metadata = metadata
        self.raw_data = raw_data

    def validate_metadata(self, index, row):
        """
        validate metadata to see if it has illegal column types
        to see if column length is integer
        :param index:
        :param row:
        :return: True or False
        """
        if row["column type"] not in self.legal_column_types:
            LOG.error("Invalid column type `{}` at line num {}".format(row["column type"], index+1))
            return False
        try:
            int(row["column length"])
        except ValueError:
            LOG.error("Invalid column length `{}` at line num {}. It should be integer.".format(row["column length"],
                                                                                                index+1))
            return False
        return True

    def load_metadata(self):
        """
        read metadata file into memory
        extract all the date column
        extract all the columns" names
        extract column_read_width for each column
        :return:
        """
        try:
            df = pd.read_csv(self.metadata,
                             sep=",",
                             names=["column name", "column length", "column type"],
                             encoding="utf-8",
                             index_col=False
                             )
            LOG.debug("Head of Metadata:")
            LOG.debug(df.head())
            for index, row in df.iterrows():
                if not self.validate_metadata(index, row):
                    return False
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
            LOG.debug(self.headers)
            LOG.debug(self.length_limit)
            LOG.debug(self.dtypes)
            LOG.debug(self.column_read_width)
            LOG.debug(self.parse_dates)
        except Exception as e:
            LOG.error(e)
            return False
        return True

    def validate_raw_data(self, index, row):
        """
        validate raw data to see if valid date for relevant column
        to see if column length is within the limit
        :param index:
        :param row:
        :return: True or False
        """
        for column in self.headers:
            if column in self.parse_dates:
                try:
                    pd.to_datetime(row[column])
                except Exception as e:
                    LOG.error("Error - Invalid Date format value: `{}` at line num {}".format(e.args[1], index+1))
                    return False
            elif len(str(row[column])) > self.length_limit[column]:
                LOG.error("Error - Invalid length of value: `{}` at line num {}. The length of column `{}` should be \
{} instead of {}".format(
                    row[column],
                    index + 1,
                    column,
                    self.length_limit[column],
                    len(str(row[column])))
                )
                return False
        return True

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
        filename = self.output_filename+"_"+str(self.output_count)+".csv"
        chunk.to_csv(filename, date_format="%d/%m/%Y", index=False)
        return filename

    def load_raw_data(self):
        """
        read data file into memory
        convert date to dd/mm/yyyy format
        output a csv file
        :param filename:
        :return:
        """
        results = []
        try:
            reader = pd.read_fwf(self.raw_data,
                                 names=self.headers,
                                 widths=self.column_read_width,
                                 parse_dates=self.parse_dates,
                                 encoding="utf-8",
                                 chunksize=self.chunk_size,
                                 )
            pool = mp.Pool(self.num_of_process)
            for chunk in reader:
                self.output_count += 1
                result = pool.apply_async(self.process_raw_data, [chunk])
                results.append(result.get())
            pool.close()
        except Exception as e:
            LOG.error("Errors while reading file: `{}`".format(self.raw_data))
            LOG.error(e)
        return results
