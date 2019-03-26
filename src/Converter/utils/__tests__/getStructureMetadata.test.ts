import { metaData, errorEmptyMetaData, errorStructureMetaData, errorTypeMetaData } from '../../data'

import getStructureMetadata from '../getStructureMetadata'

describe('getStructureMetadata', () => {
    it('converts metedata into correct format', () => {
        expect(getStructureMetadata(metaData)).toEqual([{
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
        }])
    })

    it('throws error for empty metadata', () => {
        try {
            getStructureMetadata(errorEmptyMetaData)

            // Fail test if above expression doesn't throw anything.
            expect(true).toBe(false)
        } catch (e) {
            expect(e.message).toBe('Structure metadata is empty')
        }
    })

    it('throws error when metadata has wrong length', () => {
        try {
            getStructureMetadata(errorStructureMetaData)

            // Fail test if above expression doesn't throw anything.
            expect(true).toBe(false)
        } catch (e) {
            expect(e.message).toBe('Please check the column structrue, which needs to contain only column name, column length and column type')
        }
    })

    it('throws error when metadata has wrong type', () => {
        try {
            getStructureMetadata(errorTypeMetaData)

            // Fail test if above expression doesn't throw anything.
            expect(true).toBe(false)
        } catch (e) {
            expect(e.message).toBe('Please check the column type, type of column needs to be numbe, string or date, boolean is not the correct type')
        }
    })
})
