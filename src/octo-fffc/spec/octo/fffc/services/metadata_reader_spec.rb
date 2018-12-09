require 'octo/fffc/services/metadata_reader'

include Octo::Fffc::Services

RSpec.describe Octo::Fffc::Services::MetadataReader do

    def get_service 
        MetadataReader.new
    end

    before(:context) do
        @test_file_path = "spec/octo/data/sets/01-simple/metadata.csv"
    end
    
    context '.initialize' do
        it "can initialize" do
            reader =get_service
            
            expect(reader).not_to eq(nil)
        end
    end

    context '.read_metadata' do
        it "can read" do
            reader = get_service
            
            metadata = reader.read_metadata(@test_file_path) 

            expect(metadata).not_to be(nil)

            expect(metadata[:columns].count).to eq(4)
            expect(metadata[:split_indexes].count).to eq(4)

            expect(metadata[:split_indexes]).to eq([
                { :start_index => 0, :end_index => 9  },
                { :start_index => 10, :end_index => 24  },
                { :start_index => 25, :end_index => 39  },
                { :start_index => 40, :end_index => 44  },
            ])

            expect(metadata[:columns]).to eq([
                { :name => 'Birth date', :length => 10, :type => 'date'  },
                { :name => 'First name', :length => 15, :type => 'string' },
                { :name => 'Last name',  :length => 15, :type => 'string' },
                { :name => 'Weight',     :length => 5,  :type => 'numeric' }
            ])
            
        end
    end


end