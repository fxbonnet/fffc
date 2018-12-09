require_relative "base_field"

module Octo
  module Fffc
    module Fields
      class NumericField < BaseField
        def initialize()
        end

        def field_type
          "numeric"
        end

        def convert_value(value)
          string_value = _sanitize(value)

          result = nil

          begin
            result = Float(string_value)
          rescue => e
            raise "Cannot parse value as float: [#{value}] _sanitized: [#{string_value}] error: #{e}"
          end

          log.debug "NumericField - Parsed value: [#{value}] as [#{result}]"

          return result
        end
      end
    end
  end
end
