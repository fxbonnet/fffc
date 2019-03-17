const fs = require('fs')
const moment = require('moment')
//Allow asynchronous file reading function
const Promise = require('bluebird')
//Enable loggin if not in test mode
const debug = process.env.NODE_ENV === 'test' ? false : true

//Main function
function convertFile(inputFilePath, structureFilePath, outputFile){

  Promise.props({
    inputFile: readFile(inputFilePath).then(file => file),
    structureFile: readFile(structureFilePath).then(file => file)
  })
    .then(data => {
      const structureFileArr = structureFileToArray(data.structureFile)
      const headers = getHeaders(structureFileArr)
      const content = getContent(data.inputFile, structureFileArr)
      writeFile(headers, content, outputFile)
    })
    .catch(err => console.error(`Program has stopped => ${err}`))
}

function readFile(filePath){

  return new Promise((resolve, reject) => {
    fs.readFile(filePath, ['utf8', 'r'], (err, data) => {
      if (err) reject(err)
      resolve(String(data))
    })
  })
}

function structureFileToArray(structureFile){
  debug && console.log('--Structure File--\n',structureFile)

  //filter is used to remove the last empty line
  const result = structureFile.split('\n').filter(el => !!el === true)
    .map(column => {

      if(column.split(',').length !== 3) throw 'File format is incorrect (Structure File)'

      return {
        name: column.split(',')[0],
        length: column.split(',')[1],
        type: column.split(',')[2]
      }
    })

  if(result.length === 0) throw 'File is empty (Structure File)'
  return result
}

function getHeaders(structureFileArr){
  return structureFileArr.reduce((headers, data, index) => {
    headers += index === structureFileArr.length - 1 ? `${data.name}`: `${data.name},`
    return headers
  },'')
}

function getContent(inputFile, structureFileArr){
  debug && console.log('\n--InputFile--\n',inputFile)
  debug && console.log('\n--structureFileArr\n', structureFileArr)

  return(
    inputFile.split('\n')
      .filter(el => !!el === true)
      .reduce((linesToWrite, line) => {

        structureFileArr.forEach((data, indexData) =>{

          //Remove white spaces
          const trimmedLine = line.slice(0,data.length).replace(/ /g,'')

          //Add data to the line
          if(data.type === 'date'){
            //Format date correctly if data type is date
            linesToWrite += moment(trimmedLine).format('L')
          } else {
            linesToWrite += `${trimmedLine}`
          }

          //Add a ',' or 'end of line' charater at the end of the line
          if(indexData !== structureFileArr.length - 1) linesToWrite += ','
          else linesToWrite += '\r\n'

          //Remove data from the line
          line = line.substr(data.length)
        })
        return linesToWrite
      },'')
  )
}

function writeFile(headers, content, outputFile){
  debug && console.log('--Content--\n', content)

  return new Promise((resolve, reject) => {
    fs.writeFile(outputFile, `${headers}\r\n${content}`, ['utf8', 'w'], err => {
      if (err) reject(err)
      resolve(console.log('data written on file'))
    })
  })
}

//Exports individual functions for unit testing purposes.
module.exports = {
  convertFile,
  readFile,
  structureFileToArray,
  getHeaders,
  getContent,
  writeFile
}
