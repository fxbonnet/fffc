require 'octo/fffc/clients/console_client'

include Octo::Fffc::Clients

RSpec.describe Octo::Fffc::Clients::ConsoleClient do
  
    before(:context) do
        @data_file_path     = "spec/octo/data/sets/01-simple/data.txt"
        @metadata_file_path = "spec/octo/data/sets/01-simple/metadata.csv"
        @output_file_path   = "spec/octo/data/sets/01-simple/.output/output.csv"
    end

    def get_client 
        ConsoleClient.new
    end

    context '.initialize' do
        it "can initialize" do
            client = get_client

            expect(client).not_to be(nil)
        end
    end

    context '.client_name' do
        it "returns value" do
            client = get_client
            
            expect(client.client_name).to eq('console_client')
        end
    end

    context '.run' do
        it "can run with options" do
            client = get_client
            
            result = nil

            allow(client).to receive(:_get_argv).and_return([
                "-i", @data_file_path,
                "-d", @metadata_file_path,
                "-o", @output_file_path,
                "-v"
            ])

            expect(client.run(options: nil)).to eq(0)
        end

        it "can run with no options" do
            client = get_client
            
            allow(client).to receive(:_get_argv).and_return([
               
            ])

            expect(client.run(options: nil)).to eq(1)
        end
    end

    context '._get_argv' do
        it "returns ARGV" do
            client = get_client
            
            expect(client.__send__(:_get_argv)).to eq(ARGV)
        end
    end

end  