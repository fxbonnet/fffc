const mock = jest.fn()

jest.doMock('../getCsvRows.ts', () => mock)

export default mock
