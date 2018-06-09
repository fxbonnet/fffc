package main

import (
	"github.com/ianlopshire/go-fixedwidth"
	"log"
	"github.com/gocarina/gocsv"
	"io/ioutil"
	"os"
	"fmt"
)

func main() {

	if len(os.Args) < 3 {
		fmt.Println("Not enough arguments. Please specify input and output datafiles.")
		fmt.Println("Example: fffc inputdata.txt output.csv")
		os.Exit(1)
	}

	data, err := ioutil.ReadFile(os.Args[1])

	if err != nil {
		log.Fatal(err)
	}

	people := People{}

	err = fixedwidth.Unmarshal(data, &people)

	if err != nil {
		log.Fatal(err)
	}

	people = FormatPeopleForCSVExport(people)

	csvContent, err := gocsv.MarshalString(&people)

	if err != nil {
		log.Panic(err)
	}

	err = ioutil.WriteFile(os.Args[2], []byte(csvContent), 0644)

	if err != nil {
		log.Fatal(err)
	}

}

