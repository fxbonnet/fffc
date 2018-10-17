import pandas as pd
import logging
import sys

# Logging program
LOG = logging.getLogger()


class FixedFileFormatConverter:
    legal_column_types = ["date", "numeric", "string"]
    headers = []
    parse_dates = []
    output_filename = "output.csv"

    def __init__(self):
        pass

    def validate_metadata(self, index, row):
        if row["column type"] not in self.legal_column_types:
            LOG.error("Invalid column type `{}` at line num {}".format(row["column type"], index))
            sys.exit(1)
        try:
            int(row["column length"])
        except ValueError:
            LOG.error("Invalid column length `{}` at line num {}. It should be integer.".format(row["column length"], index))
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
            LOG.debug(df.head())
            for index, row in df.iterrows():
                self.validate_metadata(index, row)
            self.headers = df[["column name"]]["column name"].tolist()
            parse_dates_df = df.loc[df["column type"] == "date"]
            self.parse_dates = parse_dates_df[["column name"]]["column name"].tolist()
            LOG.debug(self.parse_dates)
            LOG.debug(self.headers)
        except Exception as e:
            LOG.error(e)
            sys.exit(1)

    def load_data(self, filename):
        """
        read data file into memory
        convert date to dd/mm/yyyy format
        output a csv file
        :param filename:
        :return:
        """
        try:
            df = pd.read_csv(filename,
                             sep="\s+",
                             names=self.headers,
                             parse_dates=self.parse_dates,
                             error_bad_lines=False,
                             warn_bad_lines=True,
                             encoding="utf-8"
                             )
            LOG.debug(df.head())
        except Exception as e:
            LOG.error("Errors while reading file: `{}`".format(filename))
            LOG.error(e)
            sys.exit(1)
        try:
            for date_column in self.parse_dates:
                pd.to_datetime(df[date_column])
        except Exception as e:
            LOG.error("Error - Invalid Date format value: `{}`".format(e.args[1]))
            sys.exit(1)
        df.to_csv(self.output_filename, date_format="%d/%m/%Y", index=False)
