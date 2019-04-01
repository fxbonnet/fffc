import os
import csv

valid_types = ['date', 'string', 'numeric']
class MetaFileValidator ():

    def __init__ (self, file):
        self.file = file
        self.column_names = [] #list of tuples (column_name, type, length)
        self.total_column_size = 0
    
    def validate (self):
        with open(self.file, "r") as csvfile:
            for row in csvfile:
                columns = row.split (',')
                if len (columns) != 3:
                    raise Exception(f"Invalid columns in {csvfile} for the {row}")
                
                col_type = str(columns[2])
                if col_type.strip() not in valid_types:
                    print (col_type)
                    print (' '.join(valid_types))
                    raise Exception(f"{columns[2]} is not a valid type")
                
                try:
                    self.total_column_size += int (columns[1]) #add total size
                    self.column_names.append ((columns[0], columns[2], columns[1])) # example "FirstName" : (string,10)
                except Exception as e:
                    raise (e) #throw exception as is.

    def get_columns (self):
        return self.column_names
            

        
        
            