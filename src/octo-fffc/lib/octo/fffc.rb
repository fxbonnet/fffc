require "octo/fffc/version"

require "csv"

def _include_files(files, is_debug = false)
  files.each do |file|
    require "#{File.dirname(__FILE__)}/#{file}"
  end
end

def _include_folder_services(folders, is_debug = false)
  folders.each do |folder|
    Dir.glob("#{File.dirname(__FILE__)}/#{folder}/**/*.rb").each { |file|
      require file
    }
  end
end

_include_files([
  "fffc/fields/base_field.rb",
  "fffc/clients/base_client.rb",
])

_include_folder_services([
  "fffc/utils",
  "fffc/services",
  "fffc/clients",
  "fffc/fields",
])
