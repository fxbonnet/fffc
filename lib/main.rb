require_relative 'converter' 

# ruby main.rb data/data data/meta.csv data/output.csv
service = Converter.new(ARGV[0], ARGV[1], ARGV[2])
service.run!