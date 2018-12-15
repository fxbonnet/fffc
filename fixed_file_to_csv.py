#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Wed Dec 15 07:20:09 2018

@author: praveen
"""

import os
import sys
import pandas as pd
import datetime as dt
sys.tracebacklimit = 0

#get location for metadataata and fixed file format
file_path=os.path.dirname(os.getcwd())+'/octo'


#read metadata files
df_meta = pd.read_csv(file_path+"/metadata.csv",header=None)

#get values for column names, length and types
col_name=df_meta.iloc[:,0].tolist()
col_length=df_meta.iloc[:,1].tolist()
col_type=df_meta.iloc[:,2].tolist()

#read fixed file format into dataframe based on column length from metadata file
df_fwf = pd.read_fwf(file_path+'/fixed_file_format.txt', widths=col_length,\
                     encoding='utf8', header=None)

#get columns with types date and numeric
indices_date = [i for i, elem in enumerate(col_type) if 'date' in elem]
indices_num = [i for i, elem in enumerate(col_type) if 'numeric' in elem]

#get column names for fixed file dataframe
df_fwf.columns = col_name

#create data frame for date and numeric type columns
df_typedate=df_fwf.iloc[:,indices_date].copy()
df_typenum=df_fwf.iloc[:,indices_num].copy()


#print error message
try:

#convert to required date format
    for column in df_typedate:
        df_typedate[column] = pd.to_datetime(df_typedate[column])
        df_typedate[column]=df_typedate[column].dt.strftime('%d/%m/%Y')

except Exception as e_d: 
    print (e_d)


#print error message
try:
    
    for col_num in df_typenum:
        pd.to_numeric(df_typenum[col_num])
except Exception as e_n: 
    print (e_n)
   
        
#reset date columns of dataframe in required format
df_fwf.iloc[:,indices_date]=df_typedate.copy()


#output csv file
df_fwf.to_csv(file_path+"/out.csv",encoding='utf8',index=False)


