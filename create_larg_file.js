const fs = require("fs");
const file = fs.createWriteStream("./big.file");

// To create a big file for the test purpose
// this will create a big file
// Excel is not comfortable with opening csv file over a millions row
for (let i = 0; i <= 1e6; i++) {
  file.write(
    "1970-01-01John           Sm,ith     ^^^  ??   81.5contract  1989-06-11***&$\n"
  );

  file.write(
    "1975-01-31Jane           Doe,       %%%  **   61.1perman@@ent 1993-12-24\n"
  );
  file.write(
    "1988-11-28,Bob    ??       Big    !!!!        102.4permanent 2000-09-17 ?!\n"
  );
}

file.end();
