
lib = File.expand_path("../lib", __FILE__)
$LOAD_PATH.unshift(lib) unless $LOAD_PATH.include?(lib)
require "octo/fffc/version"

Gem::Specification.new do |spec|
  spec.name          = "octo-fffc"
  spec.version       = Octo::Fffc::VERSION
  spec.authors       = ["Francois-Xavier Bonnet, Nick Shulhin, OCTO Technology"]
  spec.email         = [""]

  spec.summary       = "Fixed File Format converter"
  spec.description   = "A generic tool to convert fixed file format files to a csv file based on a metadata file describing its structure."
  spec.homepage      = "https://github.com/octo-technology-downunder/fffc" 
  spec.license       = "MIT"

  # Prevent pushing this gem to RubyGems.org. To allow pushes either set the 'allowed_push_host'
  # to allow pushing to a single host or delete this section to allow pushing to any host.
  if spec.respond_to?(:metadata)
    spec.metadata["allowed_push_host"] = "TODO: Set to 'http://mygemserver.com'"
  else
    raise "RubyGems 2.0 or newer is required to protect against " \
      "public gem pushes."
  end

  # Specify which files should be added to the gem when it is released.
  spec.files         = Dir['bin/*'] 
  spec.files        += Dir['lib/**/*.rb']
  spec.files        += Dir['lib/.txt'] 
  spec.files        += Dir['lib/.md']

  spec.bindir        = "bin"
  spec.executables   = ["octo-fffc"]
  spec.require_paths = ["lib"]

  spec.add_development_dependency "bundler", "~> 1.16"
  spec.add_development_dependency "rake", "~> 10.0"
  spec.add_development_dependency "rspec", "~> 3.6.0"
end
