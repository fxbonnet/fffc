module.exports = {
    globals: {},
    env: {
        commonjs: true,
        es6: true,
    },
    parser: '@typescript-eslint/parser',
    plugins: ['@typescript-eslint'],
    parserOptions: {
        ecmaVersion: 2018,
        ecmaFeatures: {
            globalReturn: false,
        },
        sourceType: 'module',
    },
}
