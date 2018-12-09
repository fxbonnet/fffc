require 'octo/fffc/utils/options'

include Octo::Fffc::Utils

RSpec.describe Octo::Fffc::Utils::Options do

    context '.initialize' do
        it "can initialize" do
            options = Options.new
            
            expect(options).not_to eq(nil)
        end
    end

    context '.get_options' do
        it "returns nil" do
            options = Options.new
            
            opt = options.get_options
        
            expect(opt).to eq(nil)
        end

        it "returns options" do
            options = Options.new
            
            opt = options.get_options([
                "-v",
                "-i", "input-file.txt",
                "-d", "data-file.txt",
                "-o", "output-file.txt"
            ])
        
            expect(opt[:verbose]).to eq(true)
            
            expect(opt[:input_file]).to eq("input-file.txt")
            expect(opt[:data_file]).to eq("data-file.txt")
            expect(opt[:output_file]).to eq("output-file.txt")
        end
    end

end