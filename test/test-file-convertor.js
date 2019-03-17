/* global it describe */
/*es-lint disable no-undef*/
const expect = require('chai').expect
const app = require('../src/app')

const structureSample1 = 'Birth date,10,date\nFirst name,15,string\nLast name,\
15,string\nWeight,5,numeric'

const structureSample2 = 'Birth date,10,date\nFirst name,15,string\nLast name,\
15,string\nWeight,5,numeric\nDate of graduation,10,\
date\nNext of kin,15,Jon Michel\n'

const structureSample3 = ''

const structureSample4 = 'Birth date,10,date\nFirst name,15,string\nLast name,\
15,string\nWeight\n'

const structureFileArrSample1 = [
  { name: 'Birth date', length: '10', type: 'date' },
  { name: 'First name', length: '15', type: 'string' },
  { name: 'Last name', length: '15', type: 'string' },
  { name: 'Weight', length: '5', type: 'numeric' }
]

const inputFileSample = '1970-01-01John           Smith           81.5\r\n1975-01-31Jane           Doe             61.1\r\n1988-11-28Bob            Big            102.4\r\n'


describe('Structure to array function', function(){

  it('Should return an array of object with "name", "length" and "type" properties', function(done) {
    expect(app.structureFileToArray(structureSample1)[0]).to.have.all.keys('name', 'length', 'type')
    done()
  })

  it('Should return an array of object with right values for each properties', function(done) {
    expect(app.structureFileToArray(structureSample1)[2]).to.have.property('name', 'Last name')
    expect(app.structureFileToArray(structureSample1)[2]).to.have.property('length', '15')
    expect(app.structureFileToArray(structureSample1)[2]).to.have.property('type', 'string')
    done()
  })

  it('Should be able to handle any number of column', function(done){

    expect(app.structureFileToArray(structureSample2)).to.have.lengthOf(6)

    done()
  })

  it('Should return an error if file is empty', function(done){

    expect(() => app.structureFileToArray(structureSample3))
      .to.throw('File is empty (Structure File)')

    done()
  })

  it('Should return an error if file\'s format is wrong', function(done){

    expect(() => app.structureFileToArray(structureSample4))
      .to.throw('File format is incorrect (Structure File)')

    done()
  })

})

describe('Get headers from the structureFileArr', function(){

  it('Should return a string with all headers separated by a comma', function(done) {

    expect(app.getHeaders(structureFileArrSample1)).to.equal('Birth date,First name,Last name,Weight')

    done()
  })

})

describe('Get content from the input file', function(){

  it('Should transform the file to a csv file (separator \',\' and row separator CRLF)', function(done){

    expect(app.getContent(inputFileSample, structureFileArrSample1).match(/,/g).length).to.equal(9)
    expect(app.getContent(inputFileSample, structureFileArrSample1).match(/\r\n/g).length).to.equal(3)


    done()
  })

  it('Should trim trailing spaces of string ', function(done){

    expect(app.getContent(inputFileSample, structureFileArrSample1)).to.not.have.string(' ')

    done()
  })

  it('Should reformat the dates to dd/mm/yyyy', function(done){

    expect(app.getContent(inputFileSample, structureFileArrSample1).split(',')[0]).to.be.equal('01/01/1970')

    done()
  })
})

describe('Program should handle reading issues',function(){
  it('Should log an error if the input or structure file path does not exist', function(done){

    return app.readFile('wrongPath.text')
      .catch(err => {
        expect(err).to.be.not.null
      })
      .finally(done())

  })
})
