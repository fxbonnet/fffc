const filesErr = {
    inputData: 'No file chosen',
    metadata: 'No file chosen'
};
const content = {};
const inputDateFormat = 'YYYY-MM-DD';
const outputDateFormat = 'DD/MM/YYYY';

function validateFileAndSave(files, type) {
    let file;
    let acceptedExt;

    if (type === 'inputData') {
        acceptedExt = '.txt';
        file = files[0];
    } else if (type === 'metadata') {
        file = files[0];
        acceptedExt = '.csv';
    }

    if (file.name.substr(file.name.length - acceptedExt.length, acceptedExt.length).toLowerCase() != acceptedExt.toLowerCase()) {
        filesErr[type] = ("Sorry, " + file.name + " is invalid, allowed extension is: " + acceptedExt);
        alert(filesErr[type]);
    } else {
        return readFileContent(file)
            .then(fileContent => {
                content[type] = fileContent;
                delete filesErr[type];
            })
            .catch(err => {
                filesErr[type] = err;
            })
            .finally(() => {
                if (filesErr[type]) {
                    alert(filesErr[type]);
                }
            });
    }
}

function readFileContent(file) {
    return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.onload =  evt => {
            resolve(evt.target.result.split(/\r\n|\n/));
        };
        reader.readAsText(file, "UTF-8");
        reader.onerror = err => {
            reject(err);
        };
    });
}

function generateOutput() {
    if (Object.entries(filesErr).length === 0 && filesErr.constructor === Object) {
        const data = validateAndParseData();
        if (data) {
            download('Output', data);
        }
    }
}

function validateAndParseData() {
    let validData = true;
    let csvContent = '';
    const headers = [];
    const metadata = [];
    for (let i = 0, metadataRows = content.metadata.length; i < metadataRows; i++) {
        const headerData = content.metadata[i].split(',');
        if (headerData.length !== 3) {
            alert('The metadata file has invalid data');
            validData = false;
            break;
        }
        metadata.push({
            name: headerData[0],
            length: parseInt(headerData[1]),
            type: headerData[2]
        });
        headers.push(headerData[0]);
    }

    if (!validData) {
        return;
    }
    csvContent += headers.join(",");

    for (let j = 0, inputRows = content.inputData.length; j < inputRows; j++) {
        const rowString = content.inputData[j];
        csvContent += "\r\n";
        let startIndex = 0;
        const dataArray = [];
        for (let i = 0, metadataRows = metadata.length; i < metadataRows; i++) {
            const metadataRow = metadata[i];
            let data = rowString.substring(startIndex, startIndex + metadataRow.length);
            if (!data) {
                break;
            }
            switch(metadataRow.type){
                case "date":
                    if (moment(data, inputDateFormat).isValid()) {
                        data = moment(data, inputDateFormat).format(outputDateFormat);
                    }
                    else {
                        alert('Input data ' + data + ' is not formatted correctly');
                        data = null;
                        validData = false;
                    }
                    break;
                case "numeric":
                    if (/^-?(0|[1-9]\d*)(\.\d*)?$/.test(data)) {
                        data = parseFloat(data);
                    } else {
                        validData = false;
                        alert('Input data ' + data + ' is not formatted correctly');
                        data = null;
                    }
                    break;
                case "string":
                    if(/[\n\r]+/.test(data)) {
                        validData = false;
                        alert('Input data ' + data + ' is not formatted correctly');
                        data = null;
                        break;
                    } else {
                        data = data.trim();
                        if (!/^[a-zA-Z0-9]+$/.test(data)) {
                            // has something other than alphanumeric
                            data = '"' + data + '"';
                        }
                    }
            }
            if (!validData) {
                break;
            }
            dataArray.push(data);
            startIndex = startIndex + metadataRow.length;
        }

        if (!validData) {
            break;
        }
        csvContent += dataArray.join(",");
    }

    if (!validData) {
        return;
    }
    return csvContent;
}

function download(filename, text) {
    const csvData = new Blob([text], { type: 'text/csv' });
    const csvUrl = URL.createObjectURL(csvData);

    const element = document.createElement('a');
    element.href        = csvUrl;
    element.target      = '_blank';
    element.download    = 'export.csv';

    element.style.display = 'none';
    document.body.appendChild(element);

    element.click();

    document.body.removeChild(element);
}
