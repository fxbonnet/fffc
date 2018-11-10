const readline = require("readline");
const fs = require("fs");
const util = require("util");
const readFile = util.promisify(fs.readFile);

/**
 * To read a big file line by line
 * @param {String} filePath
 */
function readLine(filePath) {
  const rl = readline.createInterface({
    input: fs.createReadStream(filePath),
    crlfDelay: Infinity
  });
  return rl;
}

/**
 * load a file asynchronously
 * @param {String} metaDataFilePath
 */
async function dataColumns(metaDataFilePath) {
  return await readFile(metaDataFilePath);
}

/**
 * Remove special characters
 * @param {String} lineString
 * @param {RegExp} regex
 */
function stringSanitizer(lineString, regex = /[^a-zA-Z0-9\-,\.]/g) {
  return lineString.replace(regex, "");
}

/**
 * changed the date format from "YYYY-mm-dd" to "dd-mm-YYYY"
 * @param {String} dateString
 */
function reformatDate(dateString) {
  try {
    if (typeof Date.parse(dateString) === "number") {
      const dateElements = dateString.split("-").reverse();
      return dateElements.join("/").toString();
    }
  } catch (ex) {
    console.log(new Error(`1 File's format isn't correct`));
  }
}

/**
 * check data types of a value
 * @param {String} value
 * @param {String} dataType
 */
function checkDataType(value, dataType) {
  dataType = stringSanitizer(dataType, /\s\n\r/);
  if (dataType === "date") {
    return reformatDate(value);
  } else if (dataType === "string") {
    return value;
  } else if (dataType === "numeric") {
    try {
      return parseFloat(value);
    } catch (ex) {
      console.log(new Error("File not in the right format"));
    }
  } else {
    console.log(new Error("File not in the right format"));
  }
}

/**
 * Extract each column metadata and returns it in an array
 * @param {string} metaStirng
 */
function extractColumnsMetaData(metaStirng) {
  metaStirng = stringSanitizer(metaStirng, /\n\r/);
  return metaStirng.split(",");
}

/**
 * Create the header based on metadata
 * @param {*} metaArray
 */
function createFileHeader(metaArray) {
  header = "";
  const metaLenght = metaArray.length;
  for (let i = 0; i < metaLenght; i++) {
    if (i < metaLenght - 1) {
      header += extractColumnsMetaData(metaArray[i])[0];
      header += ",";
    } else {
      header += extractColumnsMetaData(metaArray[i])[0];
    }
  }
  return header;
}

/**
 * convers an eligible string to a csv row based on metaArray
 * @param {String} line
 * @param {Array} metaArray
 */
function lineFormater(line, metaArray) {
  let newLine = "";
  let substringFromChar = 0;
  const metaLenght = metaArray.length;
  for (let i = 0; i < metaLenght; i++) {
    const substringToChar = parseInt(extractColumnsMetaData(metaArray[i])[1]);
    const dataType = extractColumnsMetaData(metaArray[i])[2];
    let subString = line.substring(
      substringFromChar,
      substringFromChar + substringToChar
    );
    subString = checkDataType(subString, dataType.trim());
    if (i < metaLenght - 1) {
      newLine += subString;
      newLine += ",";
    } else {
      newLine += subString;
    }
    substringFromChar = substringFromChar + substringToChar;
  }
  return stringSanitizer(newLine, /\s/g);
}
/**
 * Create the data.csv file
 * @param {String} dataFile
 * @param {String} metaData
 * @param {String} wherToSave
 */
function generate_csv(dataFile, metaData, wherToSave) {
  dataColumns(metaData)
    .then(data => {
      const rl = readLine(dataFile);
      const metaArray = data.toString().split("\n");
      writeToFile(wherToSave, createFileHeader(metaArray).concat("\n"));
      rl.on("line", line => {
        const regex = new RegExp(/[^a-zA-Z0-9\-,\.\s]/g);
        line = stringSanitizer(line, regex);
        writeToFile(wherToSave, lineFormater(line, metaArray).concat("\n"));
      });
      console.log("Working...");
    })
    .catch(err => {
      console.log(new Error("something went worng"));
    });
}
/**
 * will append data to a file
 * will create a file if it does not exist
 * @param {String} path
 * @param {String} input
 */
function writeToFile(path = "datafile.csv", input) {
  var logger = fs.createWriteStream(path, {
    flags: "a" // 'a' means appending (old data will be preserved)
  });
  logger.write(input);
  logger.end();
}

module.exports.stringSanitizer = stringSanitizer;
module.exports.reformatDate = reformatDate;
module.exports.generate_csv = generate_csv;
