'use strict'
module.exports = {
    moduleFileExtensions: [
        'ts',
        'tsx',
        'js',
    ],
    transform: {
        '.tsx?$': '<rootDir>/node_modules/ts-jest/preprocessor.js',
    },
    testRegex: '(/__tests__/.*|\\.(test|spec))\\.tsx?$',
}
