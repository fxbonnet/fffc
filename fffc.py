import pandas as pd

# Importing the dataset
dataset = pd.read_csv('fffc_data.txt', sep=" ", header=None, names=["Birth date", "First name", "Last name", "Weight"])
dataset.to_csv('fffc_converted.csv', encoding='utf-8')
