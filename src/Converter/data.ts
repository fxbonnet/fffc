// data extends the data example
// added more cases to include single quote in string, minus number, empty cell, special characters
export const simpleData = `
1970-01-01John           Smith           81.5        -7    Ø
1975-01-31Jane           Doe             61.1      -2.1    @
1988-11-28Bob            Big            102.4     1.234    ß
1989-02-21Jenny          Cai            100.0 0.0000001    ñ
1990-12-23Lisa           Ho'can         99.99       666    ÷
1991-04-18Jack           Queene         70.00 -0.000002    œ
1992-06-05Gina           Hail,h                       0    €
`

// 10000 line csv file
export const large = '1970-01-01John           Smith           81.5        -7    Ø\n'.repeat(10000)

export const metaData = `
Birth date,10,date
First name,15,string
Last name,15,string
Weight,5,number
Favourite Number,10,number
Favourite Special Character,5,string
`

export const errorEmptyMetaData = ''

export const errorTypeMetaData = `
Birth date,10,date
First name,15,string
Last name,15,string
Weight,5,number
Favourite Number,10,number
Favourite Special Character,5,boolean
`

export const errorStructureMetaData = `
Birth date,10
First name,15,string
Last name,15,string
Weight,5,number
Favourite Number,10,number
Favourite Special Character,5,boolean
`


