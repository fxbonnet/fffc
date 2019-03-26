const mock = jest.fn()

jest.doMock('../exportToCsv.ts', () => mock)

export default mock
