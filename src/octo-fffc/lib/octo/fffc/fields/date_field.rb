require_relative "base_field"

module Octo
  module Fffc
    module Fields
      class DateField < BaseField
        def initialize()
        end

        def field_type
          "date"
        end

        def convert_value(value)
          string_value = _sanitize(value)

          log.debug "DateField - Parsing date: [#{string_value}] format: [#{_from_format}]"
          time_value = Date.strptime(string_value, _from_format)

          log.debug "DateField - Parsed value: [#{time_value}] format: [#{_to_format}]"
          result = time_value.strftime(_to_format)

          return result
        end

        private

        def _from_format
          "%Y-%m-%d"
        end

        def _to_format
          "%d/%m/%Y"
        end
      end
    end
  end
end
