import getStructureMetadata from '../getStructureMetadata'
import { metaData, simpleData } from '../../data'

import getCsvRows from '../getCsvRows'

describe('getCsvRows', () => {
    it('tranfers data into correct csv rows format', () => {
        const structures = getStructureMetadata(metaData)
        const rows = getCsvRows(simpleData, structures)

        expect(rows).toHaveLength(8)
        // first row to be the header
        expect(rows[0]).toEqual(['Birth date', 'First name', 'Last name', 'Weight', 'Favourite Number', 'Favourite Special Character'])

        // convert date into 'dd/mm/yyyy' format
        expect(rows[1][0]).toEqual('01/01/1970')
    })
})
