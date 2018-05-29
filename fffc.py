import pandas as pd
import pandas_validator as pv
import sys

class DataSetValidator(pv.DataFrameValidator):
    weight = pv.FloatColumnValidator('Weight', min_value=0, max_value=200)

validator = DataSetValidator()

# load meta data for columns
metadata = pd.read_csv('fffc_metadata.txt', sep=",", header=None)
columns = metadata.iloc[:, 0].values
print(columns)

# load dataset
dataset = pd.read_csv('fffc_data.txt', sep=" ", header=None, names=columns)

print(validator.is_valid(dataset))
if validator.is_valid(dataset) == False:
    print("Validation Failed")
    sys.exit(1)


# convert Birth date - column 0 to datetime for formatting
try:
    dataset[columns[0]] = pd.to_datetime(dataset[columns[0]])
except:
    print("Date is invalid - Date Conversion Failed")
    sys.exit(1)


#format date
dataset[columns[0]] = dataset[columns[0]].dt.strftime("%d/%m/%Y")

# format trailing spaces for first and last names
dataset[columns[1]] = dataset[columns[1]].map(lambda x: x.strip())
dataset[columns[2]] = dataset[columns[2]].map(lambda x: x.strip())
print(dataset)

# write the dataset to CSV
dataset.to_csv('fffc_converted.csv', encoding='utf-8', index=False)
