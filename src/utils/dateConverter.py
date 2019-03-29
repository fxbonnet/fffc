import datetime


class DateConverter():
    @staticmethod
    def convert(input):
        dt = datetime.datetime.strptime(input, '%Y-%m-%d')
        return dt.strftime('%d/%m/%Y')
