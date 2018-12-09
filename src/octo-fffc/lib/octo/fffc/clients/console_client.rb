
require_relative "api_client"

include Octo::Fffc::Clients
include Octo::Fffc::Utils

module Octo
  module Fffc
    module Clients
      class ConsoleClient < ApiClient
        def client_name
          "console_client"
        end

        def run(options: {})
          argv = _get_argv()

          options = Options.new
          option_values = options.get_options(argv)

          if option_values.nil?
            return 1
          end

          super(options: option_values)

          return 0
        end

        private

        def _get_argv
          ARGV
        end
      end
    end
  end
end
