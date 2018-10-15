#!/usr/bin/python2.7
import os
import re
import sys

'''
This function is used to check the number of arguments passed 
into the program. If number of arguments isn't equal to 4 the 
program will fail and return an error.
These are the arguments that the program expect:
    1st argument is the program name FFFtoCSV.py
    2nd argument is the metadata file name
    3rd argument is the fixed format file name
    4th argument is the name of the csv file that we want to write to
'''
def checkArgv():
    if len(sys.argv) != 4:
        errorMessage =  "Provide the correct arguments into the programs: python FFLC.py"
        errorMessage += " <metadata file> <fixed format file> <csv file to write to>"
        print errorMessage
        sys.exit()
    return True

'''
This function is used to open a file given a file's name.
The fileType is just a string that gives information about what type
of file it is (ie metadata or fixed file format). This function checks to see weather
a file is empty or doesn't exist, in this case the program will end
and print an error to stdout. If successful returns a list containing the contents
of the file
'''
def openFile(filename, fileType):
    #if file exist and not empty then read the file and store it
    if os.path.exists(filename) and os.path.getsize(filename) > 0:
        with open(filename) as f:
            fileContent = f.readlines()

        fileContent = [x.strip() for x in fileContent]
    #if file exist but is empty return an error
    elif os.path.exists(filename) and os.path.getsize(filename) == 0:
        print fileType + "is empty"
        sys.exit()
    #if file doesn't exist return an error
    else:
        print fileType + "does not exist"
        sys.exit()

    return fileContent

'''
This function is used to check that the metadata has three columns
given the content of the metadata file that is stored in the form of a 
list. As each line in the metadata seperated by "," so eg. blah,blah,blah
it checks to see if there is exactly 3 columns.If there is more or less 
than 3 colums the program will end and print an error message to stdout.
'''
def checkForThreeColumns(metaData):
    #counter to tell us the line number
    lineNumber = 1
    for index in metaData:
        #split the current index in the list using "," as a seperator
        line = index.split(",")
        #if there less than 3 columns
        if len(line) < 3:
            print "Line " + str(lineNumber) + " in metadata file is missing a column/s"
            sys.exit()
        #if there is more than 3 columns
        elif len(line) > 3:
            print "Line " + str(lineNumber) + " in metadata file has one or more too many column"
            sys.exit()
        lineNumber += 1

'''
This function is used get the first column in the metaData given
a list storing the metadata file content. It removes all trailing 
and leading white spaces. It returns an error if the column is empty.
Otherwise it will return a list containing all of column 1 in the metadata
file.
'''
def getColumnName(metaData):
    columnNameList = []
    #counter to tell us the line number
    lineNumber = 1
    for index in metaData:
        #split the the current index using the seperator ","
        listArr = index.split(",")
        #this removes trailing and leading white space
        listArr[0] = re.sub(r"^\s*", "",listArr[0])
        listArr[0] = re.sub(r"\s*$", "",listArr[0])
        #if current 1st column is empty return an error
        if listArr[0] == '':
            print "Line number " + str(lineNumber) + " column 1 in metadata file is empty"
            sys.exit()
        #otherwise add it to the name list
        else:
            columnNameList.append(listArr[0])
            lineNumber += 1;
    return columnNameList

'''
This function is used get the second column in the metaData given
a list storing the metadata file content. It removes all trailing 
and leading white spaces. It returns an error if the column is empty or not
an unsigned integer. Otherwise it will return a list containing all of column 2
in the metadata file.
'''
def getColumnLength(metaData):
    columnLengthList = []
    #counter to determine where the error is at
    lineNumber = 1
    for index in metaData:
        #split at seperator "," to get individual columns
        listArr = index.split(",")
        #remove leading and trailing white spaces
        listArr[1] = re.sub(r"^\s*", "", listArr[1])
        listArr[1] = re.sub(r"\s*$", "", listArr[1])
        #if column is empty return an error
        if listArr[1] == '':
            print "Line " + str(lineNumber) + " column 2 in metadata file is empty"
            sys.exit()
        #if column isn't an unsigned integer than return error
        elif re.match(r'^\d+$', listArr[1]) == None:
            print "Column 2 in line " + str(lineNumber) + " is not an unsigned integer"
            sys.exit()
        #otherwise add it to the list
        else:
            columnLengthList.append(listArr[1])
            lineNumber += 1;
    return columnLengthList

'''
This function is used get the third column in the metaData given
a list storing the metadata file content. It removes all trailing 
and leading white spaces. It returns an error if the column is empty or string
does not match the following options: date, numeric or string.
Otherwise it will return a list containing all of column 1 in the metadata
file.
'''
def getColumnType(metaData):
    columnTypeList = []
    #counter to keep track of which we are on
    lineNumber = 1
    for index in metaData:
        #split at seperator "," to get individual columns
        listArr = index.split(",")
        #Remove trailing and leading white spaces
        listArr[2] = re.sub(r"^\s*", "", listArr[2])
        listArr[2] = re.sub(r"\s*$", "", listArr[2])
        #if column 3 is empty return error
        if listArr[2] == '':
            print "Line " + str(lineNumber) + " column 3 in metadata file is empty"
            sys.exit()
        #calls another function to check if match the 
        #following options: date, numeric or string.
        #If the function returns false then exit program and return an
        #error
        elif checkType(listArr[2]) == False:
            print "Column 3 in line " + str(lineNumber) + " is not one of the following type: date, string or numeric"
            sys.exit()
        #if correct add it to our list
        else:
            columnTypeList.append(listArr[2])
            lineNumber += 1
    return columnTypeList

'''
This function is used to check type if the third column
in the metadata is actualy one of the following options:
date,string or numeric. Returns true if it is one of the
above option otherwise false. This takes care of any random
uppercase chars in one of the following options.
'''
def checkType(stringToCheck):
    #check to match for the string "date" or any variation of it
    #that has any uppercase
    if re.match(r'^[dD][aA][tT][eE]$', stringToCheck) != None:
        return True
    #check to match for the string "string" or any variation of it
    #that has any uppercase
    elif re.match(r'^[sS][tT][rR][iI][nN][gG]$', stringToCheck) != None:
        return True
    #check to match for the string "numeric" or any variation of it
    #that has any uppercase
    elif re.match(r'^[nN][uU][mM][eE][rR][iI][cC]$', stringToCheck) != None:
        return True
    #string passed isn't on the three options necessary
    else:
        return False

'''
This function is used to change the date format of yyyy-mm-dd
to dd/mm/yyyy. This function takes in a date format to convert
and line number to return an error to. This function also checks to
make sure to see if the date given is valid
'''
def convertDate(dateToConvert, lineNumber):
    #use capture brackets to get yyyy, mm and dd
    #basically means year is captured first, etc...
    patternToMatch = re.search(r"(\d{4})\-(\d{2})\-(\d{2})", dateToConvert)
    #store the data appropriately
    year = patternToMatch.group(1)
    month = patternToMatch.group(2)
    day = patternToMatch.group(3)
    #does a quick check to make sure that month,
    #day and year are valid (ie. month is 1-12 and with corresponding day)
    if checkValidDate(year, month, day) == False:
        print "Month or day in line " + str(lineNumber) + " of fixed format file is not in range"
    #use string concatention with get the new date format:
    #dd/mm/yyyy
    newDate = day + "/" + month + "/" + year
    return newDate

'''
This is a helper function just to check if the year, month
and day given is valid. 
'''
def checkValidDate(year, month, day):
    #two array for the numbers of day in a leap year and not 
    #leap year
    leapYear  = [31,29,31,30,31,30,31,31,30,31,30,31]
    notLeapYear = [31,28,31,30,31,30,31,31,30,31,30,31]
    #get the index of the array in the month
    monthIndex = int(month) - 1
    #determine if the year is a leap year or not
    leapYearCheck = int(year) % 4
    #check to make sure month is valid
    if int(month) < 1 or int(month) > 12:
        return False
    #check to nake sure day in the month for leap year is
    #valid
    elif leapYearCheck == 0:
        if int(day) < 1 or int(day) > int(leapYear[monthIndex]):
            return False
    #check to nake sure day in the month for non-leap year is
    #valid 
    elif leapYearCheck != 0:
        if int(day) < 1 or int(day) > int(notLeapYear[monthIndex]):
            return False
    return True

'''
This is a helper funciton to determine what the length
of each line the file data should be from the metadata
file structure. This function returns the sum of column 2 in the 
metadata file
'''
def calculateTotalLength(lengthList):
    lengthOfLine = 0
    for index in lengthList:
        lengthOfLine += int(index)
    return lengthOfLine

'''
This function takes in a list containing the fixed file format,
max index of each line in the list, the list containing the length of
each type, a list containing the type needed for each column, and a string
which is an error message if the current line of the fixed file format is
too long. It will return a list if there are no error.
'''
def ffflToCsv(fffData, maxIndex, lengthList, typeList, metadataStructure):
    csvList = []
    #calculate what the length of each line should be
    lengthCheck = calculateTotalLength(lengthList)
    #counter to determine line number
    lineNumber = 1
    #run through the list containing the fixed format file
    for index in fffData:
        #do a quick check that each line is the correct length
        #if it isn't return an error and exit the program
        if len(index) != lengthCheck:
            print "Line " + str(lineNumber) + " in fixed format file does not match metadata file structure."
            print metadataStructure
            sys.exit()
        #counter is keep track of the index in the list
        counter = 0
        #curr index is used to keep track of the index in the
        #current index 
        currIndex = 0
        #finalString is made to be empty
        finalString = ""
        #run through the current index in the list
        while counter < maxIndex:
            #get the type we need
            typeNeeded = typeList[counter]
            #get the previous index to the old current
            prevIndex = currIndex
            #calculate the current index
            currIndex += int(lengthList[counter])
            #get the current column and remove all leading
            #and trailing spaces
            originalString = index[prevIndex:currIndex]
            originalString = re.sub(r"\s*$", "", originalString)
            originalString = re.sub(r"^\s*","", originalString)
            #use a helper function to make sure that the string is of the correct type
            if checkTypeMatches(typeList[counter], originalString, counter, lineNumber) == True:
                #if the type is date change it to the new format
                if re.match(r"[dD][aA][tT][eE]", typeList[counter]) != None:
                    originalString = convertDate(originalString, lineNumber)
                if counter != maxIndex - 1:
                    finalString += originalString + ","
                else:
                    finalString += originalString + "\r\n"
            counter += 1

        csvList.append(finalString)
        lineNumber += 1
    return csvList

'''
This is a helper function that is used to check a string given matches
it's type. If the string is the correct type return true otherwise
exit the program and return an error message.
'''
def checkTypeMatches(typeToCheck, dataToCheck, column, lineNumber):
    #get the real column number
    realColumn = column + 1
    #if the type is of date check if the format of date is correct
    if re.match(r'^[dD][Aa][Tt][eE]$', typeToCheck) != None:
        if checkIfDate(dataToCheck) == False:
            print "Column " + str(realColumn) + " line " + str(lineNumber) + " date provided in fixed file format is incorrect."
            print "Date format is yyyy-mm-dd. Unsigned integers are expected"
            sys.exit()
    #if the type is of string check to see if it is a string
    elif re.match(r'^[sS][tT][rR][iI][nN][gG]$', typeToCheck) != None:
        if checkIfString(dataToCheck) == False:
            print "Column " + str(realColumn) + " line " + str(lineNumber) + " string provided in fixed file format is incorrect."
            print "String format can contain anything."
            sys.exit()
    #if the type is numeric check to make sure that is a floating point,
    #unsigned or signed number
    elif re.match(r'^[nN][uU][mM][eE][rR][iI][cC]$', typeToCheck) != None:
        if checkIfNumeric(dataToCheck) == False:
            print "Column " + str(realColumn) + " line " + str(lineNumber) + " numeric provided in fixed file format is incorrect."
            print "Numeric format can contain signed or unsigned integers or floating point number"
            sys.exit()
    return True

'''
This is a helper function that is used to make sure the format
of the date is correct
'''
def checkIfDate(dataToCheck):
    if re.match(r'\d{4}\-\d{2}\-\d{2}', dataToCheck) == None:
        return False
    return True

'''
This is a helper funtion that is used to make sure that the
numeric format is correct (floating point and unsigned or signed)
'''
def checkIfNumeric(dataToCheck):
    if re.match(r'^\-{,1}\d*\.{0,1}\d*$', dataToCheck) == None:
        return False
    return True

'''
This is a helper function that is used to make sure that format of
string is correct
'''
def checkIfString(dataToCheck):
    if re.match(r'^.*$', dataToCheck) == None:
        return False
    return True

'''
This is a helper function that is used to get the first
column of the metadata file to add it to the first line
of the csv file.
'''
def getFirstColumn(columnNameList, sizeOfList):
    index = 0
    string = ""
    while index < sizeOfList:
        if index != (sizeOfList - 1):
            string += columnNameList[index] + ","
        else:
            string += columnNameList[index] + "\r\n"
        index += 1
    return string

'''
This is helper function that gets the metadata file 
structure which is the type required along with it's
corresponding length this returns a string with the
structure of the metadata file
'''
def getMetaDataStructure(typeList, lengthList):
    i = 0
    metadataStructure = "Metadata structure required is: "
    while i < len(typeList):
        if i != len(typeList) - 1:
            metadataStructure += str(typeList[i]) + " with length of " + str(lengthList[i]) + ", "
        else:
            metadataStructure += str(typeList[i]) + " with length of " + str(lengthList[i]) + "\n"

        i += 1
    return metadataStructure

'''
This function is used for writing to the csv file.
This function takes in the list containing the csv
format along with the name of the file it has to write
to.
'''
def writeToCsvFile(csvFile, csvList):
    #opens up a file or creates a new
    #with mode to write
    with open(csvFile, 'w') as f:
        for index in csvList:
            f.write("%s" % index)


#the program starts here
#checks to see if there is 4 arguments passed through
if checkArgv() == True:
    metadataFile = sys.argv[1]
    fixedFormatFile = sys.argv[2]
    csvFile = sys.argv[3]

#Open the metadata file and read it
metaDataContent = openFile(metadataFile, "Metadata file ")
#check to make sure that there is 3 columns in the metadata
#file. Return an error if there isn't 3 columns in the metadata
#file
checkForThreeColumns(metaDataContent)
#get the first column of the metadata file and return
#a list containing this if successful
columnNameList = getColumnName(metaDataContent)
#get the second column of the metadata file and return
#a list containing this if successful
columnLengthList = getColumnLength(metaDataContent)
#get the third column of the metadata file and return
#a list containing this if successful
columnTypeList = getColumnType(metaDataContent)
#get the metadata file structure which is it's length and type
metadataStructure = getMetaDataStructure(columnTypeList, columnLengthList)
#open the fixed file format and store it into a list
fffContent = openFile(fixedFormatFile, "Fixed file format file ")
#Convert the fixed file content to csv file
csvList = ffflToCsv(fffContent, len(columnLengthList), columnLengthList, columnTypeList, metadataStructure)
#get the first line of the csv file line and add it
csvFirstLine = getFirstColumn(columnNameList, len(columnNameList))
csvList.insert(0, csvFirstLine)
#make the csv file and write to it
writeToCsvFile(csvFile, csvList)
