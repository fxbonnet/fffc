

//load dov env
require('dotenv').load();
var fileReader = require("./fileReader");



fileReader.readFile(process.env.INPUT_DATA_URL, process.env.OUTPUT_DATA_URL, process.env.META_DATA_URL).then((res) => {
    console.log("File saved as " + process.env.OUTPUT_DATA_URL);
    console.log(res + " lines are converted ");
}, (err) => {
    console.error('\x1b[31m Could not convert file because of error: %s\x1b[0m', err);
});


