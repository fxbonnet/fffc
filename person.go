package main

import (
	"time"
	"log"
	"strconv"
)

// A Person holds the data for importing from fixed width data files and exporting to a csv
type Person struct {
	BirthDate string `fixed:"1,10",csv:"Birth date"`
	FirstName string `fixed:"11,25",csv:"First name"`
	LastName  string `fixed:"26,40",csv:"Last name"`
	Weight    string `fixed:"41,45",csv:"Weight"`
}

// People is a list of persons
type People []Person

// FormatPeopleForCSVExport takes a people list and prepares the data for exporting to CSV
func FormatPeopleForCSVExport(people People) (People) {

	for k, person := range people {

		err := ValidateWeightForCSVExport(person.Weight)

		if err != nil {
			log.Println("Failed parsing weight on line " + strconv.Itoa(k) + ". Error: ")
			log.Fatal(err)
		}

		person.BirthDate, err = ConvertDateToCSVExportFormat(person.BirthDate)

		if err != nil {
			log.Println("Failed parsing date on line " + strconv.Itoa(k) + ". Error: ")
			log.Fatal(err)
		}

	}

	return people
}

// ConvertDateToCSVExportFormat takes a "2006-01-02" formatted date and converts it to "02/01/2006"
func ConvertDateToCSVExportFormat(date string) (string, error) {

	dateFormat := "2006-01-02"

	formattedDate, err := time.Parse(dateFormat, date)

	if err != nil {
		return date, err
	}

	return formattedDate.Format("02/01/2006"), err
}

// ValidateWeightForCSVExport validates that a string is a valid float.
func ValidateWeightForCSVExport(weight string) (error) {

	_, err := strconv.ParseFloat(weight, 64)

	return err
}
