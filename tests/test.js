const chai = require('chai');
const expect = chai.expect; // we are using the "expect" style of Chai
const fileReader = require('../fileReader');
const fs = require("fs");
const assert = require('assert')

require('dotenv').load();

describe('parseData', function () {
    it('parseData() should return in correct date format', function () {
        expect(fileReader.parseData("1998-08-23", "date")).to.equal("23/08/1998");

    });
    it('parseData() should return in correct numeric format', function () {
        expect(fileReader.parseData("-123.2", "numeric")).to.equal("-123.2");
    });
    it('parseData() should return in correct string format', function () {
        expect(fileReader.parseData("Jane", "string")).to.equal("Jane");
    });

});


describe('handleLine', function () {
    it('handleLine() should convert a line from raw data to CSV line (date, string)', function () {
        expect(fileReader.handleLine("1970-01-01John",
            [{ name: 'Birth date', length: 10, type: 'date' },
            { name: 'First name', length: 15, type: 'string' }]))
            .to.equal("01/01/1970,John");

    });
    it('handleLine() should convert a line from raw data to CSV line (string, date)', function () {
        expect(fileReader.handleLine("John1970-01-01",
            [{ name: 'First name', length: 4, type: 'string' },
            { name: 'Birth date', length: 15, type: 'date' }]))
            .to.equal("John,01/01/1970");

    });
    it('handleLine() should convert a line from raw data to CSV line (string, date, numeric)', function () {
        expect(fileReader.handleLine("John 1970-01-01 123.33",
            [{ name: 'First Name', length: 5, type: 'string' },
            { name: 'Birth date', length: 10, type: 'date' },
            { name: 'Weight', length: 15, type: 'numeric' }]))
            .to.equal("John,01/01/1970,123.33");
    });

});


describe('file Tests', function () {
    describe('file Test1', function () {
        let output;
        before(function (done) {
            fileReader.readFile(process.env.SAMPLE1_INPUT, process.env.SAMPLE1_OUTPUT, process.env.SAMPLE1_META)
                .then((res) => {
                    output = {
                        result: fs.readFileSync(process.env.SAMPLE1_OUTPUT, 'utf8'),
                        expected: fs.readFileSync(process.env.SAMPLE1_EXPECTED, 'utf8')
                    }
                    done();
                }, (err) => {
                    console.error('\x1b[31m%s\x1b[0m', err);
                    done();
                })
        });
        it('Sample 1 output file should be the same', function () {
            expect(output.result).to.equal(output.expected);
        });
    })

    describe('file Test2', function () {
        let output;
        before(function (done) {
            fileReader.readFile(process.env.SAMPLE2_INPUT, process.env.SAMPLE2_OUTPUT, process.env.SAMPLE2_META)
                .then((res) => {
                    output = {
                        result: fs.readFileSync(process.env.SAMPLE2_OUTPUT, 'utf8'),
                        expected: fs.readFileSync(process.env.SAMPLE2_EXPECTED, 'utf8')
                    }
                    done();
                }, (err) => {
                    console.error('\x1b[31m%s\x1b[0m', err);
                    done();
                })
        });
        it('Sample 2 with empty file output file should be empty with cols', function () {
            expect(output.result).to.equal(output.expected);
        });
    })

    describe('file Test3', function () {
        let output;
        before(function (done) {
            fileReader.readFile(process.env.SAMPLE3_INPUT, process.env.SAMPLE3_OUTPUT, process.env.SAMPLE3_META)
                .then((res) => {
                    output = {
                        result: fs.readFileSync(process.env.SAMPLE3_OUTPUT, 'utf8'),
                        expected: fs.readFileSync(process.env.SAMPLE3_EXPECTED, 'utf8')
                    }
                    done();
                }, (err) => {
                    output = err
                    done();
                })
        });
        it('Sample 3 should throw error about date format', function () {
            expect(output instanceof Error).to.equal(true);
            expect(output.toString()).to.equal("Error: Wrong date format");
        });
    })

    describe('file Test4', function () {
        let output;
        before(function (done) {
            fileReader.readFile(process.env.SAMPLE4_INPUT, process.env.SAMPLE4_OUTPUT, process.env.SAMPLE4_META)
                .then((res) => {
                    output = {
                        result: fs.readFileSync(process.env.SAMPLE4_OUTPUT, 'utf8'),
                        expected: fs.readFileSync(process.env.SAMPLE4_EXPECTED, 'utf8')
                    }
                    done();
                }, (err) => {
                    output = err
                    done();
                })
        });
        it('Sample 4 should throw error about numeric format', function () {
            expect(output instanceof Error).to.equal(true);
            expect(output.toString()).to.equal("Error: Wrong numeric format");
        });
    })

    
})


