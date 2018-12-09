require "optparse"

module Octo
  module Fffc
    module Utils
      class Options
        def get_options(argv = [])
          options = {}
          optparse = nil

          begin
            optparse = OptionParser.new do |opts|
              opts.banner = "Usage: octo-fffc [options]"

              opts.on("-v", "--[no-]verbose", "run verbosely") do |v|
                options[:verbose] = true
              end

              opts.on("-i", "--input-file=value", "input file") do |v|
                options[:input_file] = v
              end

              opts.on("-d", "--data-file=value", "metadata file") do |v|
                options[:data_file] = v
              end

              opts.on("-o", "--output-file=value", "output file") do |v|
                options[:output_file] = v
              end
            end

            optparse.parse(argv)

            mandatory = [:input_file, :data_file]
            missing = mandatory.select { |param| options[param].nil? }

            unless missing.empty?
              raise OptionParser::MissingArgument.new(missing.join(", "))
            end
          rescue OptionParser::InvalidOption, OptionParser::MissingArgument
            puts $!.to_s
            puts optparse

            options = nil
          end

          return options
        end
      end
    end
  end
end
