import moment from 'moment'

import { MetaData } from './getStructureMetadata'
import { Row } from './exportToCsv'

/**
 * To convert fixed file into rows for CSV conversion
 * @param file file that for converting into CSV
 * @param structures structure for each column
 */
export default function getCsvRows(file: string, structures: MetaData[]): Row[] {
    const headerRow = structures.map(structure => structure.columnName)

    const fileArray = file.split('\n').filter(row => !!row)

    const contentRows = fileArray.map(row => {
        let start = 0

        return structures.reduce((acc: string[], column) => {
            let trimmedContent = row.slice(start, start + column.columnLength).replace(/ /g,'')

            start = start + column.columnLength

            if (column.columnType === 'date') {
                trimmedContent = moment(trimmedContent).format('L')
            }

            acc.push(trimmedContent)

            return acc
        }, [])
    })

    contentRows.unshift(headerRow)

    return contentRows
}
