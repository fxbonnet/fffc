import pandas as pd


class FixedFileFormatConverter:
    data_column_property = []
    headers = []

    def __init__(self):
        pass

    def load_metadata(self, filename):
        with open(filename) as f:
            lines = f.readlines()
            for line in lines:
                item = line.strip().split(",")
                column = {
                    "column_name": item[0],
                    "column_length": item[1],
                    "column_type": item[2],
                }
                self.data_column_property.append(column)
                self.headers.append(item[0])

    def load_data(self, filename):
        df = pd.read_csv(filename, sep=";", names=self.headers)
        df['Birth date'] = pd.to_datetime(df['Birth date'])
        df.to_csv('output', date_format='%d/%m/%Y', index=False)