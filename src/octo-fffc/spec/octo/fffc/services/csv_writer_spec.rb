require 'octo/fffc/services/csv_file_writer'

include Octo::Fffc::Services

RSpec.describe Octo::Fffc::Services::FileReader do

    before(:context) do
        @test_file_path = "spec/octo/data/sets/01-simple/.output/output.csv"
    end

    def get_service 
        CsvFileWriter.new(path: @test_file_path)
    end

    context '.initialize' do
        it "can initialize" do
            writer = get_service 
            
            expect(writer).not_to eq(nil)
        end
    end
    
end