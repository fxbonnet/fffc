import re
import datetime
import pandas as pd
from datetime import date
import pdb


with open('metadata.txt') as f:
    conf = f.readlines()


cols = [str(i.split(',')[0]).strip() for i in conf]
data_types = [str(i.split(',')[2]).strip() for i in conf]

df = pd.DataFrame(columns=cols)



with open('data.txt') as d:
    data = d.readlines()

contents = [line.rstrip() for line in data]

for i in contents:
    line_new = []
    temp_line = 1

    for dtype in data_types:
        if dtype == 'date':
            match = re.search(r'(\d+-\d+-\d+)',str(i))
            date = datetime.datetime.strptime(match.group(), '%Y-%m-%d').date()
            temp_line = str(i).replace(str(date), '')
            line_new.append(date.strftime('%d/%m/%y'))
        if dtype == 'string':
            x = [i for i in temp_line.split() if isinstance(i,str)]
            temp_line = temp_line.replace(str(x[0]), '')
            line_new.append(x[0])
        if dtype == 'numeric':
            x = re.findall("\d+\.\d+", temp_line)
            if x != []:
                line_new.append(x[0])
            elif x == []:
                z = re.findall("\d", temp_line)[0]
                temp_line = temp_line.replace(str(x[0]), '')
                line_new.append(z)
    df.loc[len(df)] = line_new

df.to_csv('output_file.csv', index=False)
