require 'octo/fffc/clients/base_client'

include Octo::Fffc::Clients

RSpec.describe Octo::Fffc::Clients::BaseClient do
  
    def get_client 
        BaseClient.new
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
            
            expect(client.client_name).to eq('base_client')
        end
    end

    context '.run' do
        it "can run" do
            client = get_client
            
            expect {
                client.run(options: {})
            }.not_to raise_error
        end
    end

end  