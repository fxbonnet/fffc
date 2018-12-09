require "octo/fffc/utils/loggable"

module Octo
  module Fffc
    module Fields
      class BaseField
        include Octo::Fffc::Utils::Loggable

        def initialize()
        end

        def field_type
          raise "field_type() should be implemented"
        end

        def convert_value(value)
          return value
        end

        private

        def _sanitize(value)
          if value.nil?
            return ""
          end

          value = value.to_s.strip

          value
        end
      end
    end
  end
end
