const expect = chai.expect;
describe("FFFC Tests", () => {
	beforeEach(function() {
		alert = sinon.spy();
	});
	describe("validate file and save method", () => {
		it('should fail and show error if input file is not of txt format', done => {
			const file = new File(["foo"], "file.doc", {
				type: "text/plain",
			});
			validateFileAndSave([file], 'inputData');
			expect(alert.calledOnce).to.be.true;
			expect(alert.args[0][0]).to.equal('Sorry, file.doc is invalid, allowed extension is: .txt');
			expect(filesErr.inputData).to.equal('Sorry, file.doc is invalid, allowed extension is: .txt');
			done();
		});

		it('should fail and show error if metadata file is not of csv format', done => {
			const file = new File(["foo"], "file.doc", {
				type: "text/plain",
			});
			validateFileAndSave([file], 'metadata');
			expect(alert.calledOnce).to.be.true;
			expect(alert.args[0][0]).to.equal('Sorry, file.doc is invalid, allowed extension is: .csv');
			expect(filesErr.metadata).to.equal('Sorry, file.doc is invalid, allowed extension is: .csv');
			done();
		});

		it('should set input content if file format is txt', done => {
			const file = new File(["1970-01-01John           Smith           81.5"], "file.txt", {
				type: "text/plain",
			});

			validateFileAndSave([file], 'inputData').then(() => {
				expect(alert.calledOnce).to.be.false;
				expect(content.inputData).to.equal(["1970-01-01John           Smith           81.5"]);
				expect(filesErr.inputData).to.equal(undefined);
			}).finally(done);
		});

		it('should set metadata content if file format is txt', done => {
			const file = new File(["Birth date,10,date"], "file.csv", {
				type: "text/csv",
			});

			validateFileAndSave([file], 'metadata').then(() => {
				expect(alert.calledOnce).to.be.false;
				expect(content.metadata).to.equal(["Birth date,10,date"]);
				expect(filesErr.metadata).to.equal(undefined);
			}).finally(done);
		});
	});

	describe("validate and parse data method", () => {
		beforeEach(() => {
			content.metadata = [
				"Birth date,10,date",
				"First name,15,string",
				"Last name,15,string",
				"Weight,5,numeric"
			];
		});

		it("should check and fail if metadata does not have 3 columns", () => {
			content.metadata = [
				"Birth date,10",
				"First name,15",
				"Last name,15",
				"Weight,5"
			];
			const csvContent = validateAndParseData();
			expect(alert.calledOnce).to.be.true;
			expect(alert.args[0][0]).to.equal('The metadata file has invalid data');
			expect(csvContent).to.equal();
		});

		it("should check and fail if date input format is not yyyy-mm-dd", () => {
			content.inputData = ["1970-29-12John           Smith          81.5"];
			const csvContent = validateAndParseData();
			expect(alert.calledOnce).to.be.true;
			expect(alert.args[0][0]).to.equal('Input data 1970-29-12 is not formatted correctly');
			expect(csvContent).to.equal();
		});

		it("should check date input format to be yyyy-mm-dd and reformat to dd/mm/yyyy", () => {
			content.inputData = ["1970-12-29John           Smith          81.5"];
			const csvContent = validateAndParseData();
			expect(alert.calledOnce).to.be.false;
			expect(csvContent).to.equal("Birth date,First name,Last name,Weight\r\n29/12/1970,John,Smith,81.5");

		});

		it("should check and fail if numeric field to has symbols other than .", () => {
			content.inputData = ["1970-12-12John           Smith          1,815"];
			const csvContent = validateAndParseData();
			expect(alert.calledOnce).to.be.true;
			expect(alert.args[0][0]).to.equal('Input data 1,815 is not formatted correctly');
			expect(csvContent).to.equal();
		});

		it("should check numeric field to not have symbols other than .", () => {
			content.inputData = ["1970-12-12John           Smith          181.5"];
			const csvContent = validateAndParseData();
			expect(alert.calledOnce).to.be.false;
			expect(csvContent).to.equal("Birth date,First name,Last name,Weight\r\n12/12/1970,John,Smith,181.5");
		});

		it("should trim trailing spaces from strings", () => {
			content.inputData = ["1970-12-12John           Smith          181.5"];
			expect(validateAndParseData()).to.equal("Birth date,First name,Last name,Weight\r\n12/12/1970,John,Smith,181.5");
		});
		it("should generate csv content with first row containing column names", () => {
			content.inputData = ["1970-12-12John           Smith          181.5"];
			expect(validateAndParseData()).to.equal("Birth date,First name,Last name,Weight\r\n12/12/1970,John,Smith,181.5");
		});
		it("should escape the string with \" if it contains symbols", () => {
			content.inputData = ["1970-12-12John           Smith+         181.5"];
			expect(validateAndParseData()).to.equal("Birth date,First name,Last name,Weight\r\n12/12/1970,John,\"Smith+\",181.5");
		});
		it("should check and fail if string contains \r or \n", () => {
			content.inputData = ["1970-12-12John           Smith\nas       181.5"];
			const csvContent = validateAndParseData();
			expect(alert.calledOnce).to.be.true;
			expect(alert.args[0][0]).to.equal('Input data Smith\nas        is not formatted correctly');
			expect(csvContent).to.equal();
		});
	});
});
