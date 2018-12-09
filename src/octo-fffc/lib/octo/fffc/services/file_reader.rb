
require "octo/fffc/utils/loggable"

module Octo
  module Fffc
    module Services
      class FileReader
        include Octo::Fffc::Utils::Loggable

        def initialize()
        end

        def read_lines(file_path)
          log.debug("Reading file lines: #{file_path}")

          File.foreach(file_path) do |line|
            yield({
                   :file_path => file_path,
                   :line => line,
                 })
          end

          log.debug("Finished reading lines for file: #{file_path}")
        end
      end
    end
  end
end
