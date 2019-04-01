import os
from utils import MetaFileVaIidator as mf
from utils import ConvertValueToType as cv
import argparse
import csv

def main ():
    parser = argparse.ArgumentParser()
    parser.add_argument("--input_data_file", help="input file path for the data file.",
                    action="store", default="input/data.csv")
    parser.add_argument("--input_meta_data_file", help="input file path for the metadata file.",
                    action="store", default="input/metadata.csv")
    parser.add_argument("--output_file", help="filename of the output file.",
                    action="store", default="output.csv")
    
    args = parser.parse_args()
    m = mf.MetaFileValidator(args.input_meta_data_file)
    m.validate() #validate the meta data file and throws error if invalid format.
    columns = m.get_columns () #get the total number of columns with length and type.

    total_size_of_columns = m.total_column_size
    
    #add the row header with column  names to first row in output file.
    header_row = []
    for i in columns:
        header_row.append(i[0].strip())
    
    first_row = True
    with open (args.input_data_file, "rt") as csvFile, open (args.output_file, "wt", newline='') as output_file:
        datareader = csv.reader(csvFile)
        datawriter = csv.writer(output_file)
        
        if first_row:
            datawriter.writerow (header_row)
            first_row = False

        #process the file as you read.
        for row in datareader:
            #print (row)
            row = str (row).strip('[]').strip('\'') #convert list to string.
            if len(row) > total_size_of_columns:
                raise Exception (f"size of the {row} is invalid.")
            new_row = []
            i = 0
            for col in columns:
                col_name = col[0].strip()
                col_type = col[1].strip()
                col_length = col[2].strip()
                
                # convert the value to the desired format based on type.
                s = row[i:i+int(col_length)]
                new_value = cv.ConvertValueToType (s, col_type).convert()
                
                i += int(col_length)
                new_row.append(new_value.strip())
        
             #write the converted row to output file.
            print (f'adding new row {new_row}')
            datawriter.writerow (new_row)

    #close file handler.
    csvFile.close()
    output_file.close()


if __name__ == "__main__":
    main()


            




