import getStructureMetadata from './getStructureMetadata'
import getCsvRows from './getCsvRows'
import exportToCsv from './exportToCsv'

/**
 * To combine the whold conversion utils into one flow
 * @param structureMetadata structrue meta data used for the given fixed file data
 * @param fixedFileData raw data for csv conversion
 */
export default function convertFileToCsv(structureMetadata: string, fixedFileData: string) {
    const structures = getStructureMetadata(structureMetadata)
    const rows = getCsvRows(fixedFileData, structures)
    exportToCsv('export.csv', rows)
}
