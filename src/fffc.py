from utils.metaDataHandler import MetaDataHandler


def gen_header(mdh):
    return csv_formater([c.name for c in mdh.columns])


def total_length(mdh):
    return sum([c.length for c in mdh.columns])


def parse_a_row(mdh, row):
    cols = []
    prev = 0
    for i, c in enumerate(mdh.columns):
        col = row[prev: prev+c.length]
        try:
            cols.append(c.typeConverter.convert(col))
        except ValueError:
            raise ValueError('parsing error for column %d, %s. The content is: %s' % (i + 1, c.name, col))
        prev += c.length
    return cols


def csv_formater(src_list):
    return ','.join(['"' + col.replace('"', '""').replace(',', '\,') + '"' for col in src_list]) + '\n'


def main(meta_data_csv, input_filename, output_filename):
    mdh = MetaDataHandler(meta_data_csv)
    tot_length = total_length(mdh)
    line_counter = 0

    print('Totally %d columns specified.'%(len(mdh.columns)))
    print(mdh.columns)

    with open(input_filename, encoding='utf-8', newline='', mode='r') as input, \
            open(output_filename, encoding='utf-8', mode='w') as output:
        output.writelines(gen_header(mdh))
        for row in input.readlines():
            if len(row.strip()) != tot_length:
                raise ValueError('Length of line %d is incorrect. real/spec is %d/%d' % (line_counter, len(row), tot_length))

            try:
                row_out = parse_a_row(mdh, row.strip())
            except ValueError as e:
                raise ValueError('Parsing error on line %d, with error message: %s' % (line_counter + 1, e) )
            output.writelines(csv_formater(row_out))
            line_counter += 1
    print('Totally %d rows converted.' % line_counter)


if __name__ == '__main__':
    main('../input/metadata.csv', '../input/data.fff', '../output/output.csv')
