export type ColumnType = 'number' | 'date' | 'string'

export interface MetaData {
    columnName: string,
    columnLength: number,
    columnType: ColumnType
}

/**
 * To convert metaData to structure array for export file into CSV
 * @param metaData explain the column structure of the final converted csv
 */
export default function getStructureMetaData(metaData: string): MetaData[] {
    let structureArray: MetaData[] = []

    const structures = metaData.split('\n').filter(line => !!line)

    if (!structures.length) {
        throw new Error('Structure metadata is empty')
    }

    structureArray = structures.map(structure => {
        const structureLine = structure.split(',')

        // incorrect column structure
        if (structureLine.length !== 3) {
            throw new Error(`Please check the column structrue, which needs to contain only column name, column length and column type`)
        }

        const columnType = structureLine[2]

        // if columnType is not number, string or date, throw error
        if (columnType !== 'number' && columnType !== 'date' && columnType !== 'string') {
            throw new Error(`Please check the column type, type of column needs to be numbe, string or date, ${columnType} is not the correct type`)
        }

        return {
            columnName: structureLine[0],
            columnLength: parseInt(structureLine[1], 10),
            columnType: structureLine[2] as ColumnType,
        }
    })

    return structureArray
}
