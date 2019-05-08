# Fixed File Format converter

Your goal is to write a generic tool to convert fixed file format files to a csv file based on a metadata file describing its structure.

Feel free to use your favorite language and libraries if needed (but no proprietary libraries, only open source), fork this project and provide your complete code as a pull request (including source and tests).

## Use case

Our fixed file format files can have any number of columns
A column can be of 3 formats:
* date (format yyyy-mm-dd)
* numeric (decimal separator '.' ; no thousands separator ; can be negative)
* string

The structure of the file is described in a metadata file in csv format with a line for each column defining:
* column name
* column length
* column type

You should transform the file to a csv file (separator ',' and row separator CRLF)

The dates have to be reformatted to dd/mm/yyyy

The trailing spaces of string columns must be trimmed

The csv file must include a first line with the columns names

## Example

Data file:
```
1970-01-01John           Smith           81.5
1975-01-31Jane           Doe             61.1
1988-11-28Bob            Big            102.4
```

Metadata file:
```
Birth date,10,date
First name,15,string
Last name,15,string
Weight,5,numeric
```

Output file:
```
Birth date,First name,Last name,Weight
01/01/1970,John,Smith,81.5
31/01/1975,Jane,Doe,61.1
28/11/1988,Bob,Big,102.4
```

## Extra requirements
* files are encoded in UTF-8 and may contain special characters
* strings columns may contain separator characters like ',' and then the whole string needs to be escaped with " (double quotes). Only CR or LF are forbidden
* in case the format of the file is not correct, the program should fail but say explicitly why
* a fixed format file may be very big (several GB)


## Tips

**What do we assess in the solution proposed?**

The way you solve the problem, apply best practices and follow clean code and software craftsmanship principles. 

**What do we NOT assess in the solution proposed?**

- Frameworks/Technologies that you know

During our face to face interview, we assess your technical knowledge. Therefore it's not necessary at this stage to showcase your knowledge about frameworks that you may know. But feel free to use them if you think it makes sense.

**Timeline to finish the exercise**

We appreciate you taking the time to do this exercise and as such there is no hard deadline for finishing it. Also, we don't want you to spend too much time in solving this exercise by creating very complex solution. Focus on delivering a good quality solution rather than a complex solution.

**Why do the coding exercise?** 

- We are a technology driven company, value software craftsmanship principles and passion for a clean code. 
- No matter how experienced our consultants are, or what position they are in, coding is a part of their day to day job and the role we offer is very hands-on. 
- We would like to make sure that anyone who joins us shares these values as well. So show us your passion for coding through this exercise!

**Programming Language** 

You can use any language that you feel comfortable with to solve the problem


