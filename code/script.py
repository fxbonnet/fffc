# Mohammad hamzehloui
# mohammad.hamzehloui@gmail.com

import csv
import datetime
import os
import string


class CSV_Converter:

    def __init__(self, metadata, flat_files, results_path):
        self.metadata = self.reading_metadata(metadata)
        self.flat_files = self.reading_flat_files(flat_files)
        self.results_path = results_path
        if os.path.isfile(results_path): os.remove(self.results_path)

    def reading_metadata(self, metadata_file):
        rows = []
        try:
            with open(metadata_file, 'r') as metadata:
                for row in csv.reader(metadata):
                    rows.append(row)
                return rows
        except IOError as e:
            print("Could not read the metadata file.")
            exit()

    # yield is used to read one line at the time as oppose to reading the whole file at once.

    def reading_flat_files(self, flat_file):
        try:
            with open(flat_file, "r") as flatfile:
                for line in flatfile:
                    yield line
        except IOError:
            print("Could not read the flat files")
            exit()

    def check_date(self, date, line_number):
        try:
            datetime.datetime.strptime(date, '%Y-%m-%d')
        except ValueError:
            print("The entry {} for the date, is in a wrong format. Line number: {}".format(date, line_number))
            exit()

    def is_string(self, name, line_number):
        try:
            isinstance(name, str)

        except ValueError:
            print("The value {} is not a string, line number : {}".format(name, line_number))
            exit()

    def is_numeric(self, number, line_number):
        try:
            float(number)
        except ValueError:
            print("The value {} is not numeric, line number : {}".format(number, line_number))
            exit()

    def validation(self, List, line_number):
        List = iter(List)
        for j in self.metadata:
            value = next(List)
            if j[2] == 'date':
                self.check_date(value, line_number)

            if j[2] == 'string':
                self.is_string(value, line_number)

            if j[2] == 'numeric':
                self.is_numeric(value, line_number)

    def pre_write(self, data):
        for index, value in enumerate(data):
            if any(char in set(string.punctuation.replace("-", "").replace('.', '')) for char in value):
                data[index] = '"{}"'.format(value)

        data[0] = datetime.datetime.strptime(data[0], '%Y-%m-%d').strftime('%d-%m-%Y')
        self.creating_csv(data)

    def creating_csv(self, data):
        file_exist = os.path.isfile(self.results_path)
        header = [x[0] for x in self.metadata]

        with open(self.results_path, 'a') as csvFile:
            if not file_exist:
                writer = csv.DictWriter(csvFile, fieldnames=header, lineterminator='\r\n', quotechar="'")
                writer.writeheader()

            writer = csv.writer(csvFile, lineterminator='\r\n', quotechar="'")
            writer.writerow(data)
            csvFile.close()

    def main(self):
        List = []
        line_number = 0
        size = list(map(int, [x[1] for x in self.metadata]))

        for line in self.flat_files:
            line_number += 1
            for i in size:
                Slice = line[:i].strip()
                List.append(Slice)
                line = line[i:]
            self.validation(List, line_number)  # if true continue else ...
            self.pre_write(List)
            List = []
