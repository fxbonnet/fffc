require 'byebug'
require 'csv'
require_relative 'formatter'

class Converter
    def initialize(data_file, meta_file, output_file)
        @data_file = data_file
        @meta_file = meta_file
        @output_file = output_file
    end

    def run!
        if !File.exist?(@data_file) || !File.exist?(@meta_file)
            raise RuntimeError.new('Data/Meta files should be provided')
        end
        
        meta_fields = read_meta_data
        headers = meta_fields.map { |key, _| key }
        CSV.open(@output_file, 'w', :write_headers=> true, :headers => headers) do |csv|
            File.open(@data_file, 'r').each do |line|
                csv << parse_line(line, meta_fields)
            end
        end

        true
    rescue Exception => e
        raise e
    end

    private

    def read_meta_data
        fields = Hash.new
        CSV.foreach(@meta_file, headers: false) do |row|
            fields[row[0]] = { :length => row[1], :type => row[2] }
        end
        fields
    end

    def parse_line(line, meta_fields)
        line_dup = line.clone
        values = []
        meta_fields.each do |key, value|
            values << format(extract_value(line_dup, value[:length]), value[:type])
        end
        values
    end

    def extract_value(line, length)
        line.slice!(0..length.to_i-1)
    end

    def format(value, type)
        formatter = Formatter.new(type)
        formatter.format(value)
    end
end