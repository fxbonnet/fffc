var readFile = require("./readFile");

test("Change date format from 'YYYY-mm-dd' to 'dd/mm/YYYY'", () => {
  expect(readFile.reformatDate("1970-03-02")).toStrictEqual("02/03/1970");
});

test("Remove special characters (!@#$%^&*\\s) form the string", () => {
  expect(readFile.stringSanitizer("!it@_w or#$%ked^&* ")).toStrictEqual(
    "it_worked"
  );
});
