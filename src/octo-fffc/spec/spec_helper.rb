require "bundler/setup"
require 'simplecov'

simplecov_coverage = ENV['OCTO_FFFC_SIMPLECOV_COVERAGE'] || 90

SimpleCov.start do
  # excluding folders from SimpleCov coverage report
  add_filter '/bin'
  add_filter '/coverage'
  add_filter '/spec'
  add_filter '/test'
end

SimpleCov.minimum_coverage simplecov_coverage

require "octo/fffc"

RSpec.configure do |config|
  # Enable flags like --only-failures and --next-failure
  config.example_status_persistence_file_path = ".rspec_status"

  # Disable RSpec exposing methods globally on `Module` and `main`
  config.disable_monkey_patching!

  config.expect_with :rspec do |c|
    c.syntax = :expect
  end
end
