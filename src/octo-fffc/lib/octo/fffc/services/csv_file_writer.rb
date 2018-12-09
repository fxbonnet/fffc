
require "octo/fffc/utils/loggable"

require "csv"
require "fileutils"

module Octo
  module Fffc
    module Services
      class CsvFileWriter
        include Octo::Fffc::Utils::Loggable

        def initialize(path:)
          @path = path
        end

        def write_row(row)
          file_handle << row
        end

        def close
          if !file_handle.nil?
            log.debug("Closing CSV handle: #{@path}")

            file_handle.close
            file_handle = nil
          end
        end

        private

        def file_handle
          if @file.nil?
            log.debug("Ensuring dir path for file: #{@path}")
            _ensure_dir(@path)

            log.debug("Opening CSV file: #{@path}")
            @file = CSV.open(@path, "wb")
          end

          @file
        end

        def _ensure_dir(path)
          dir_path = File.dirname(path)
          FileUtils.mkdir_p(dir_path)
        end
      end
    end
  end
end
