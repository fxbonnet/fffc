import exportToCsvMock from '../__mocks__/exportToCsvMock'
import getCsvRowsMock from '../__mocks__/getCsvRowsMock'
import getStructureMetadataMock from '../__mocks__/getStructureMetadataMock'
import { metaData, simpleData } from '../../data'

import convertFileToCsv from '../convertFileToCsv'

describe('convertFileToCsv', () => {
    const structures = [{
        columnName: 'Birth date',
        columnLength: 10,
        columnType: 'date',
    }, {
        columnName: 'First name',
        columnLength: 15,
        columnType: 'string',
    }, {
        columnName: 'Last name',
        columnLength: 15,
        columnType: 'string',
    }, {
        columnName: 'Weight',
        columnLength: 5,
        columnType: 'number',
    }, {
        columnName: 'Favourite Number',
        columnLength: 10,
        columnType: 'number',
    }, {
        columnName: 'Favourite Special Character',
        columnLength: 5,
        columnType: 'string',
    }]

    beforeEach(() => {
        getStructureMetadataMock.mockReturnValue(structures)
    })

    it('converts file to csv by correct flow', () => {
        convertFileToCsv(metaData, simpleData)

        expect(getStructureMetadataMock).toHaveBeenCalledWith(metaData)
        expect(getCsvRowsMock).toHaveBeenCalledWith(simpleData, structures)
        expect(exportToCsvMock).toHaveBeenCalledTimes(1)
    })
})
