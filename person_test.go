package main

import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestValidateWeightForCSVExportFailsWhenInvalidValues(t *testing.T) {

	values := []string{"100a", "1,0", "wrong"}

	for _, v := range values {
		err := ValidateWeightForCSVExport(v)
		assert.NotNil(t, err)
	}
}

func TestValidateWeightForCSVExportPassesWhenValidValues(t *testing.T) {

	values := []string{"100", "-100", "12.123", "-45.123"}

	for _, v := range values {
		err := ValidateWeightForCSVExport(v)
		assert.Nil(t, err)
	}
}

func TestConvertDateToCSVExportFormatFailsOnInvalidFormat(t *testing.T) {

	values := []string{"1970-13-01", "1975-01-31a", "1988-11-40"}

	for _, v := range values {
		_, err := ConvertDateToCSVExportFormat(v)
		assert.NotNil(t, err)
	}
}

func TestConvertDateToCSVExportFormatParsesDatesCorrectly(t *testing.T) {

	values := map[string]string{
		"1970-01-01": "01/01/1970",
		"1975-01-31": "31/01/1975",
		"1988-11-28": "28/11/1988",
	}

	for k, v := range values {
		parsedDate, err := ConvertDateToCSVExportFormat(k)
		assert.Nil(t, err)
		assert.Equal(t, v, parsedDate)
	}
}
