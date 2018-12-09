require_relative "base_client"

include Octo::Fffc::Clients
include Octo::Fffc::Services
include Octo::Fffc::Utils

module Octo
  module Fffc
    module Clients
      class ApiClient < BaseClient
        def client_name
          "api_client"
        end

        def run(options:)
          options = _default_options.merge(options)
          super(options: options)

          begin
            metadata_reader = MetadataReader.new

            file_reader = FileReader.new
            csv_writer = CsvFileWriter.new(
              :path => options[:output_file],
            )

            _process_file(
              metadata_reader,
              file_reader,
              csv_writer,
              options
            )
          rescue => e
            log.error("Error during file processing: #{e}")
            raise
          ensure
            if !csv_writer.nil?
              csv_writer.close()
            end
          end

          log.info("Completed!")
        end

        private

        def _process_file(metadata_reader, file_reader, csv_writer, options)
          log.info("Reading metadata file: #{options[:data_file]}")
          metadata = metadata_reader.read_metadata(options[:data_file])

          log.info("Processing input data file: #{options[:input_file]}")
          file_reader.read_lines(options[:input_file]) { |line_data|
            line = line_data[:line]

            log.debug("Processing line: #{line}")
            row = _process_line(
              line_data[:line],
              metadata
            )

            csv_writer.write_row(row)
          }

          log.info("Saved processed data to file: #{options[:output_file]}")
        end

        def _default_options
          {
            :input_file => "input.txt",
            :data_file => "data.csv",
            :output_file => "output.csv",
            :verbose => false,
          }
        end

        def _process_line(line, metadata)
          result = []

          begin
            columns = metadata[:columns]
            split_indexes = metadata[:split_indexes]

            column_index = 0

            split_indexes.each do |split_index|
              start_index = split_index[:start_index]
              end_index = split_index[:end_index]

              column = columns[column_index]

              string_value = line[start_index..end_index]
              log.debug(" - raw value: #{string_value}")

              field_value = _process_field_value(column[:type], string_value)
              result << field_value

              column_index = column_index + 1
            end
          rescue => e
            log.error("Error during column processing: #{e}")
            log.error("- line data: #{line}")
            log.error("- metadata: #{metadata}")

            raise
          end

          return result
        end

        def _process_field_value(field_type, string_value)
          column = _lookup_column(field_type)
          return column.convert_value(string_value)
        end

        def _lookup_column(field_type)
          _init_column_classes()

          column = @column_classes[field_type]

          if column.nil?
            raise "Cannot find column implementation for type: #{field_type}"
          end

          column
        end

        def _init_column_classes()
          if @column_classes.nil?
            @column_classes = {}

            classes = ClassUtils.load_octo_classes(
              parent_class: Octo::Fffc::Fields::BaseField,
            )

            classes.each do |field_class|
              if field_class == Octo::Fffc::Fields::BaseField
                next
              end

              field_instance = field_class.new()
              @column_classes[field_instance.field_type] = field_instance
            end
          end

          @column_classes
        end
      end
    end
  end
end
