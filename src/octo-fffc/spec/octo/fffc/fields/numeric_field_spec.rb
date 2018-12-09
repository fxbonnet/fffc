require 'octo/fffc/fields/numeric_field'

include Octo::Fffc::Fields

RSpec.describe Octo::Fffc::Fields::BaseField do
  
    def get_field 
        NumericField.new
    end

    context '.initialize' do
        it "can initialize" do
            field = get_field
            expect(field).not_to be(nil)
        end
    end

    context '.field_type' do
        it "raises exception" do
            field = get_field

            expect(field.field_type).to eq('numeric')
        end
    end

    context '.convert_value' do
        it "parses valid values" do
            field = get_field

            data = {
                '1'     => 1,
                2.3     => 2.3,
                '4.5'   => 4.5,
                ' 1 '   => 1,
                ' 4.5 ' => 4.5
            }

            data.each do | key, value |
                expect(field.convert_value(key)).to eq(value)
            end
        end

        it "raises on invalid values" do
            field = get_field

            data = {
                nil => nil,
                {}  => nil,
                ""  => nil,
                []  => nil
            }

            data.each do | key, value |
                expect {
                    field.convert_value(key)
                }.to raise_error(/Cannot parse value as float/)
            end
        end
    end

end  