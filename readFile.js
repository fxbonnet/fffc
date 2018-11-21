const readline = require("readline");
const fs = require("fs");
const util = require("util");
const readFile = util.promisify(fs.readFile);
let recordProcessed = 0;

/**
 * To read a big file line by line
 * @param {String} filePath
 */
function readLine(filePath) {
  const rl = readline.createInterface({
    input: fs.createReadStream(filePath).on("error", () => {
      console.log(new Error(`Make sure ${filePath} exist`));
      return;
    }),
    crlfDelay: Infinity
  });
  return rl;
}

/**
 * load a file asynchronously
 * @param {String} metadataFilePath
 */
async function dataColumns(metadataFilePath) {
  return await readFile(metadataFilePath);
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
    const regex = /([12]\d{3,3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01]))/;
    const match = dateString.match(regex);
    if (match) {
      const dateElements = dateString.split("-").reverse();
      return dateElements.join("/").toString();
    } else {
      throw new Error(
        `${dateString} not a correct data format. check metadata maps with data`
      );
    }
  } catch (ex) {
    console.log(
      new Error(
        `${dateString} not a correct data format. check metadata maps with data`
      )
    );
    process.exit(1);
  }
}

/**
 * check data types of a value
 * @param {String} value
 * @param {String} dataType
 */
function checkDataType(value, dataType) {
  dataType = stringSanitizer(dataType, /\s\n\r/);
  try {
    if (dataType === "date") {
      return reformatDate(value);
    } else if (dataType === "string") {
      return value;
    } else if (dataType === "numeric") {
      try {
        return parseFloat(value);
      } catch (ex) {
        console.log(
          new Error(`${value} is not a number. check metadata maps with data`)
        );
        process.exit(1);
      }
    } else {
      throw new Error(
        `Data file value: ${value} not matching data type: ${dataType}`
      );
    }
  } catch (ex) {
    console.log(ex.message);
    process.exit(1);
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
  let headerArray = [];
  const metaLength = metaArray.length;
  for (let i = 0; i < metaLength; i++) {
    headerArray.push(extractColumnsMetaData(metaArray[i])[0]);
  }
  return headerArray.join();
}

/**
 * convers an eligible string to a csv row based on metaArray
 * @param {String} line
 * @param {Array} metaArray
 */
function lineFormater(line, metaArray) {
  let lineArray = [];
  let substringFromChar = 0;
  try {
    const metaLength = metaArray.length;
    for (let i = 0; i < metaLength; i++) {
      const substringToChar = parseInt(extractColumnsMetaData(metaArray[i])[1]);
      const dataType = extractColumnsMetaData(metaArray[i])[2];
      let subString = line.substring(
        substringFromChar,
        substringFromChar + substringToChar
      );
      subString = checkDataType(subString, dataType.trim()).toString();
      if (subString.includes(",")) {
        subString = '"'.concat(subString).concat('"');
      }
      lineArray.push(stringSanitizer(subString, /\s/g));
      substringFromChar = substringFromChar + substringToChar;
    }
    return lineArray.join();
  } catch (ex) {
    console.log(ex.message);
    process.exit(1);
  }
}
/**
 * Create the data.csv file
 * @param {String} fixedFileFormat
 * @param {String} metadataFile
 * @param {String} wherToSave
 */
function generate_csv(fixedFileFormat, metadataFile, wherToSave) {
  dataColumns(metadataFile)
    .then(data => {
      const rl = readLine(fixedFileFormat);
      const metaArray = data.toString().split("\n");
      const regex = new RegExp(/[^a-zA-Z0-9\-,\.\s]/g);
      writeToFile(wherToSave, createFileHeader(metaArray).concat("\n"));
      rl.on("line", line => {
        try {
          line = stringSanitizer(line, regex);
          writeToFile(wherToSave, lineFormater(line, metaArray).concat("\n"));
        } catch (ex) {
          console.log(ex.message);
          clearInterval(refreshInterval);
          process.exit(1);
        }
      });
      console.log(`Please wait...`);
    })
    .catch(err => {
      console.log(new Error(err.message));
      clearInterval(refreshInterval);
      return;
    });
}
/**
 * will append data to a file
 * will create a file if it does not exist
 * @param {String} path
 * @param {String} input
 */
function writeToFile(path = "datafile.csv", input) {
  try {
    var logger = fs.createWriteStream(path, {
      flags: "a"
    });
    logger.write(input);
    recordProcessed++;
  } catch (ex) {
    console.log(new Error("While saving the file something went wrong"));
  } finally {
    logger.end();
  }
}
let z = 0;
const refreshInterval = setInterval(() => {
  if (z === recordProcessed) {
    clearInterval(refreshInterval);
    console.log(`\nDone.`);
    process.exit(0);
  }
  z = recordProcessed;
  process.stdout.write(
    `\t${recordProcessed.toLocaleString()} records processed`
  );
  process.stdout.cursorTo(0, process.stdout);
}, 500);

module.exports.stringSanitizer = stringSanitizer;
module.exports.reformatDate = reformatDate;
module.exports.generate_csv = generate_csv;
