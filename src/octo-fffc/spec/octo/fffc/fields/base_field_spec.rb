require 'octo/fffc/fields/base_field'

include Octo::Fffc::Fields

RSpec.describe Octo::Fffc::Fields::BaseField do
  
    context '.initialize' do
        it "can initialize" do
            field = BaseField.new
            expect(field).not_to be(nil)
        end
    end

    context '.field_type' do
        it "raises exception" do
            field = BaseField.new

            expect {
                field.field_type  
            }.to raise_error(/should be implemented/)
        end
    end

    context '.convert_value' do
        it "returns input value" do
            field = BaseField.new

            data = [
                '1',
                2,
                3.3,
                nil,
                -1,
                [],
                {}
            ]

            data.each do | value |
                expect(field.convert_value(value)).to eq(value)
            end
        end
    end

    context '._sanitize' do
        it "returns sanitized value" do
            field = BaseField.new

            data = {
                nil  => '',
                '1 ' => '1',
                ' 2' => '2',
                ' 3 ' => '3',
                '   4   ' => '4'
            }

            data.each do | key, value |
                expect(field.__send__(
                    :_sanitize,
                    key)
                ).to eq(value)
            end
        end
    end

end  