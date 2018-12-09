require "logger"

module Octo
  module Fffc
    module Utils
      module Loggable
        @@verbose = false

        def set_verbose
          @@verbose = true
        end

        def set_info
          @@verbose = false
        end

        def log
          if @logger.nil?
            @logger = _get_logger()
            _configure_logger(@logger)
          end

          @logger
        end

        def _get_logger()
          logger = Logger.new(STDOUT)

          if @@verbose == true
            logger.level = Logger::DEBUG
          else
            logger.level = Logger::INFO
          end

          return logger
        end

        def _configure_logger(logger)
          logger.formatter = proc do |severity, datetime, progname, message|
            Loggable._format_message(severity, datetime, progname, message)
          end
        end

        def self._format_message(severity, datetime, progname, message)
          color_code = Loggable._get_message_color_code(severity: severity, message: message)

          if severity == "DEBUG"
            message = "\t#{message}"
          end

          "\e[#{color_code}m#{datetime} #{severity} #{message}\e[0m\n"
        end

        def self._get_message_color_code(severity:, message:)
          color = _white

          case severity
          when "INFO"
            color = _green
          when "WARN"
            color = _yellow
          when "DEBUG"
            color = _light_blue
          when "ERROR"
            color = _red
          end

          return color
        end

        def self._white
          37
        end

        def self._red
          31
        end

        def self._green
          32
        end

        def self._yellow
          33
        end

        def self._blue
          34
        end

        def self._pink
          35
        end

        def self._light_blue
          36
        end

        def self._gray
          37
        end
      end
    end
  end
end
