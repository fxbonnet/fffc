const mock = jest.fn()

jest.doMock('../getStructureMetadata.ts', () => mock)

export default mock
