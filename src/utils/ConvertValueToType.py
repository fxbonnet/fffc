import os
import re
from datetime import datetime

class ConvertValueToType ():

    def __init__ (self, value, column_type):
        self.value = str(value)
        self.column_type = column_type.strip()
    
    def convert (self):
        if self.column_type in "string":
            #regex to check for special characters.
            regex = re.compile(',[@_!#$%^&*()<>?/\|}{~:]')
            if(regex.search(self.value) == None):
                return self.value
            else:
                self.value = "\"" + self.value + "\""
                return self.value
        
        if self.column_type in "date":
            convert_date = datetime.strptime(self.value, '%Y-%m-%d')
            return convert_date.strftime('%d/%m/%Y')
        
        if self.value in "numeric":
            #self.value = str(float(self.value.strip()))
            return self.value

        return self.value


    
