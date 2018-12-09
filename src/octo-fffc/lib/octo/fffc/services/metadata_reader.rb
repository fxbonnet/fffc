
require "octo/fffc/utils/loggable"
require "csv"

module Octo
  module Fffc
    module Services
      class MetadataReader
        include Octo::Fffc::Utils::Loggable

        def initialize()
        end

        def read_metadata(file_path)
          log.debug("Reading metadata: #{file_path}")
          metadata = CSV.read(file_path)

          columns = _get_columns(metadata)
          split_indexes = _get_split_indexes(columns)

          return {
                   :columns => columns,
                   :split_indexes => split_indexes,
                 }
        end

        private

        def _get_columns(metadata)
          result = []

          metadata.each do |metadata_field|
            result << {
              :name => metadata_field[0],
              :length => metadata_field[1].to_i,
              :type => metadata_field[2].to_s.downcase,
            }
          end

          return result
        end

        def _get_split_indexes(columns)
          result = []

          start_index = 0

          columns.each do |column|
            end_index = start_index + column[:length] - 1

            result << {
              :start_index => start_index,
              :end_index => end_index,
            }

            start_index = start_index + column[:length]
          end

          return result
        end
      end
    end
  end
end
