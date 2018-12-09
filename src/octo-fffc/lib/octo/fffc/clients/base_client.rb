require "octo/fffc/utils/loggable"

module Octo
  module Fffc
    module Clients
      include Octo::Fffc::Utils::Loggable

      class BaseClient
        def initialize()
          log.info("Starting octo-fffc util, client: #{client_name}, v#{Octo::Fffc::VERSION}")
        end

        def client_name
          "base_client"
        end

        def run(options: {})
          options = {
            :verbose => false,
          }.merge(options)

          _configure_logging(options[:verbose])
        end

        private

        def _configure_logging(verbose = false)
          if verbose == true
            set_verbose()
          else
            set_info()
          end
        end
      end
    end
  end
end
