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
    try {
        const structures = metaData.split('\n').filter(line => !!line)

        if (!structures.length) {
            throw new Error('Structure metadata ')
        }

        structureArray = structures.map(structure => {
            const structureLine = structure.split(',')

            return {
                columnName: structureLine[0],
                columnLength: parseInt(structureLine[1], 10),
                columnType: structureLine[2] as ColumnType,
            }
        })
    } catch (error) {
        structureArray = []
    }

    return structureArray
}
