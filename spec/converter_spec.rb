require 'spec_helper'
 
describe Converter do
    context '#valid' do
        before :each do
            @service = Converter.new('data/data.min', 'data/meta.csv', 'data/output.csv')
        end

        it 'converts the file' do
            expect(@service.run!).to eq(true)
        end
    end

    context '#invalid' do
        it 'raises an error' do
            @service = Converter.new('NO_FILE', 'NO_META.csv', 'output.csv')
            expect { @service.run! }.to raise_error(RuntimeError, 'Data/Meta files should be provided')
        end

        it 'raises an error' do
            @service = Converter.new('data/data.min.faulty', 'data/meta.csv', 'data/output.csv')
            expect { @service.run! }.to raise_error(RuntimeError, 'Wrong Numeric Format')
        end        
    end    
end