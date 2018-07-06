import csv
import datetime
import sys


def slices(s, lengths):
    position = 0
    for l in lengths:
        yield s[position:position + l]
        position += l


def formatNumeric(n):
    return float(n) if '.' in n else int(n)


def formatDate(d):
    return datetime.datetime.strptime(d, '%Y-%m-%d').strftime('%d/%m/%Y')


# set of arbitrary separators.
separators = ',; '

def formatString(s):
    s = s.rstrip()
    return '"{}"'.format(s) if any(i in s for i in separators) else s


def formatType(v, t):
    t = t.lower()
    if t == 'numeric':
        return formatNumeric(v)
    elif t == 'string':
        return formatString(v)
    elif t == 'date':
        return formatDate(v)
    else:
        raise Exception('unknown data conversion type {}'.format(t))


if __name__ == '__main__':

    if len(sys.argv) != 4:
        print('Expects 3 arguments: metadata filename, data filename, output filename')
        sys.exit(1)

    metadata_filename = sys.argv[1]
    data_filename = sys.argv[2]
    output_filename = sys.argv[3]

    metadata = []

    with open(metadata_filename, 'r') as csvfile:
        metareader = csv.reader(csvfile, delimiter=',')
        for row in metareader:
            metadata.append(row)

    fieldwidths = [int(row[1]) for row in metadata]

    types = [row[2] for row in metadata]

    bunchsize = 1000  # batch write size
    bunch = []
    with open(data_filename, "r") as r, open(output_filename, "w") as w:

        # header
        bunch.append(','.join([row[0] for row in metadata]) + '\r\n')

        for i, line in enumerate(r):

            fields = slices(line, fieldwidths)
            fields_f = []
            for j, (v, t) in enumerate(zip(fields, types)):

                try:
                    fields_f.append(str(formatType(v, t)))

                except Exception as e:
                    raise Exception(
                        'failed to convert column[{0}] on line[{1}].  value[{2}], type[{3}]'.format(j + 1, i + 1, v, t))



            bunch.append(','.join(fields_f) + '\r\n')
            # todo remove final crlf

            if len(bunch) == bunchsize:
                w.writelines(bunch)
                bunch = []

        w.writelines(bunch)