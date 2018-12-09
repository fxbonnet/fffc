require_relative "base_field"

module Octo
  module Fffc
    module Fields
      class StringFiels < BaseField
        def initialize()
        end

        def field_type
          "string"
        end

        def convert_value(value)
          result = _sanitize(value)
          log.debug "StringFiels - Parsed value: [#{value}] as [#{result}]"

          return result
        end
      end
    end
  end
end
