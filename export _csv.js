const { generate_csv } = require("./readFile");

const datafile = "./big.file";
const metaData = "./input/metadata.txt";
const whereToSave = "./datafile.csv";

generate_csv(datafile, metaData, whereToSave);
