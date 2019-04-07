require 'date'

class Formatter
    attr_accessor :type

    def initialize(type)
        @type = type
    end

    module Adapters
        module DateFormatter
            def self.format(value)
                Date.parse(value.to_s).strftime('%d/%m/%Y')
            rescue Exception => e
                raise RuntimeError.new('Wrong Date Format')
            end
        end

        module NumericFormatter
            def self.format(value)
                Float(value)
            rescue Exception => e
                raise RuntimeError.new('Wrong Numeric Format')
            end
        end

        module StringFormatter
            def self.format(value)
                value.force_encoding('utf-8').encode('utf-8').strip
            rescue Exception => e
                raise RuntimeError.new('Wrong String Format')
            end
        end
    end

    def adapter
        Formatter::Adapters.const_get("#{@type.to_s.capitalize}Formatter")
    end

    def format(value)
        adapter.format(value)
    end
end