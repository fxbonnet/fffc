

const fs = require("fs");
const rl = require('readline');
const es = require('event-stream')

module.exports = {
    metadataReader: function (url) {
        var metaArr = [];
        return new Promise((resolve, reject) => {

            fs.readFile(url, 'utf8', (err, data) => {
                if (err) {
                    reject(err);
                    return false;
                }

                if (!data) {
                    reject(new Error("Empty file"));
                    return false;
                }
                for (meta of data.split('\n')) {
                    if (meta) {
                        let line = meta.split(',');
                        metaArr.push({
                            name: line[0],
                            length: parseInt(line[1]),
                            type: line[2]
                        });
                    }
                }
                resolve(metaArr);
            });
        })

    },

    parseData: function (rawData, type) {

        switch (type) {
            case "date":
                if (rawData.match(/\d\d\d\d-\d\d-\d\d/)) {
                    return rawData.split('-')[2] + '/'
                        + rawData.split('-')[1] + '/'
                        + rawData.split('-')[0]
                } else {
                    throw new Error("Wrong date format")
                }
                break;
            case "numeric":
                if (rawData.match(/-?[1-9.]+/)) {
                    return rawData;
                } else {
                    throw new Error("Wrong numeric format")
                }
                break;
            case "string":
                if (rawData.match(/[^\n\r]+/)) {
                    if (rawData.indexOf(',') === -1) {
                        return rawData;
                    } else {
                        return '"' + rawData + '"'
                    }
                    break;
                } else {
                    throw new Error("Wrong string format")
                }

        }
    },
    handleLine: function (line, metaData) {
        let outputLine = "";
        for (meta of metaData) {
            let rawData = line.slice(0, meta.length).trim();

            line = line.slice(meta.length);
            try {
                outputLine = outputLine + this.parseData(rawData, meta.type) + ',';
            } catch (e) {
                return e
            }

        }
        return outputLine.slice(0, -1);
    },


    readFile: function (inputURL, outputURL, metaURL) {

        let metaArr;
        let that = this;
        let lineCount = 0;
        return new Promise((resolve, reject) => {
            this.metadataReader(metaURL).then((metaArr) => {
                let lineReader = rl.createInterface({
                    input: fs.createReadStream(inputURL)
                });
                let lineWriter = fs.createWriteStream(outputURL)

                //write header
                for (let i = 0; i < metaArr.length; i++) {
                    lineWriter.write(metaArr[i].name);
                    if (i != metaArr.length - 1)
                        lineWriter.write(",");
                }
                lineWriter.write("\r\n", "utf8");

                lineReader.on('line', function (line) {
                    lineCount++;
                    if (line) {
                        let result = that.handleLine(line, metaArr);

                        if (result instanceof Error) {
                            return reject(result)
                        }
                        lineWriter.write(result, "utf8");
                        lineWriter.write("\r\n", "utf8");

                    }
                });
                lineReader.on("close", () => {
                    lineWriter.end();
                })
                lineWriter.on("finish", () => {
                    resolve(lineCount);
                });
            }, (err) => {
                reject(err);
            })
        });

    }

}

