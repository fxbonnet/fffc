import datetime


def parse_date(value):
    try:
        return datetime.datetime.strptime(value, "%Y-%m-%d").strftime("%d/%m/%Y")
    except ValueError:
        return None