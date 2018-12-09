require 'octo/fffc/clients/api_client'

include Octo::Fffc::Clients

RSpec.describe Octo::Fffc::Clients::ApiClient do
  
    before(:context) do
        @data_file_path     = "spec/octo/data/sets/01-simple/data.txt"
        @metadata_file_path = "spec/octo/data/sets/01-simple/metadata.csv"
        @output_file_path   = "spec/octo/data/sets/01-simple/.output/output.csv"
    end

    def get_client 
        ApiClient.new
    end

    context '.initialize' do
        it "can initialize" do
            client = get_client

            expect(client).not_to be(nil)
        end
    end

    context '.run' do
        it "can run" do
            client = get_client
            
            data = client.run(options: {
                :input_file  => @data_file_path,
                :data_file   => @metadata_file_path,
                :output_file => @output_file_path
            })
        end

        it "fails on file processing" do
            client = get_client
            
            allow(client).to receive(:_process_file) .and_raise('Cannot open file')

            expect {
                client.run(options: {
                    :input_file  => @data_file_path,
                    :data_file   => @metadata_file_path,
                    :output_file => @output_file_path
                })
            }.to raise_error(/Cannot open file/)
        end

        it "fails on file line processing" do
            client = get_client
            
            allow(client).to receive(:_process_field_value) .and_raise('Cannot parse line')

            expect {
                client.run(options: {
                    :input_file  => @data_file_path,
                    :data_file   => @metadata_file_path,
                    :output_file => @output_file_path
                })
            }.to raise_error(/Cannot parse line/)
        end
    end

    context '._lookup_column' do
        it "can lookup field" do
            client = get_client
            
            field = client.__send__(:_lookup_column, "string")
            expect(field).not_to be(nil)
        end

        it "can lookup field" do
            client = get_client
            
            expect {
                client.__send__(:_lookup_column, "custom_field")
            }.to raise_error(/Cannot find column implementation for type/)
        end
    end

end  