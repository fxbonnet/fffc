const fs = require("fs");
const file = fs.createWriteStream("./big.file");

//To create a big file for the test purpouse
for (let i = 0; i <= 1e6; i++) {
  file.write(
    "1970-01-01John           Smith           81.5contract  1989-06-11\n"
  );
  file.write(
    "1975-01-31Jane           Doe             61.1permanent 1993-12-24\n"
  );
  file.write(
    "1988-11-28Bob            Big            102.4permanent 2000-09-17\n"
  );
}

file.end();
