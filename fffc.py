import pandas as pd

# load meta data for columns
metadata = pd.read_csv('fffc_metadata.txt', sep=",", header=None)
columns = metadata.iloc[:, 0].values
print(columns)

# load dataset
dataset = pd.read_csv('fffc_data.txt', sep=" ", header=None, names=columns)
# convert column 0 which is date to datetime for formatting
dataset[columns[0]] = pd.to_datetime(dataset[columns[0]])
#format date
dataset[columns[0]] = dataset[columns[0]].dt.strftime("%d/%m/%Y")
print(dataset)

# write the dataset to CSV
dataset.to_csv('fffc_converted.csv', encoding='utf-8', index=False)
