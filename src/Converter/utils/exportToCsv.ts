export type Row = string[]

/**
 * convert row data into the correct format for csv conversion
 * @param row data of each row for csv conversion
 */
export function processRow(row: Row) : string {
    let finalVal = ''
    for (let j = 0; j < row.length; j++) {
        const innerValue = row[j] === null ? '' : row[j].toString()

        let result = innerValue.replace(/"/g, '""')

        if (result.search(/("|,|\n)/g) >= 0) {
            result = `"${result}"`
        }

        if (j > 0) {
            finalVal = `${finalVal},`
        }

        finalVal = `${finalVal}${result}`
    }
    return `${finalVal} \n`
}

/**
 * To convert the row data into csv and download it into local
 * @param fileName the name of the csv file
 * @param rows row data for csv conversion
 */
export default function exportToCsv(fileName: any, rows: Row[]) {
        let csvFile = ''
        for (let i = 0; i < rows.length; i++) {
            csvFile += processRow(rows[i])
        }

        const blob = new Blob([csvFile], { type: 'text/csv;charset=utf-8;' })
        if (navigator.msSaveBlob) { // IE 10+
            navigator.msSaveBlob(blob, fileName)
        } else {
            const link = document.createElement('a')

            // Browsers that support HTML5 download attribute
            if (link.download !== undefined) {
                const url = URL.createObjectURL(blob)

                link.setAttribute('href', url)
                link.setAttribute('download', fileName)
                link.style.visibility = 'hidden'
                document.body.appendChild(link)
                link.click()
                document.body.removeChild(link)
            }
        }
    }
