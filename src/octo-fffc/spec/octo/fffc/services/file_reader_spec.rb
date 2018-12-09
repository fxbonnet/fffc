require 'octo/fffc/services/file_reader'

include Octo::Fffc::Services

RSpec.describe Octo::Fffc::Services::FileReader do

    def get_service 
        FileReader.new
    end

    before(:context) do
        @test_file_path = "spec/octo/data/sets/01-simple/data.txt"
    end
    
    context '.initialize' do
        it "can initialize" do
            reader = get_service 
            
            expect(reader).not_to eq(nil)
        end
    end

    context '.read_lines' do
        it "can read lines" do
            reader = get_service 
            
            result = []

            reader.read_lines(@test_file_path) { | line_data |
                result << line_data[:line]
            }

            expect(result).not_to eq([])
            expect(result.count).to eq(3)

            expect(result[0]).to eq("1970-01-01John           Smith           81.5\n")
            expect(result[1]).to eq("1975-01-31Jane           Doe             61.1\n")
            expect(result[2]).to eq("1988-11-28Bob            Big            102.4")
        end
    end


end