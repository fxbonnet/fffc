
require "objspace"

module Octo
  module Fffc
    module Utils
      module ClassUtils
        extend self

        def load_octo_classes(parent_class:, class_prefix: "Octo::")
          result = []
          classes = load_classes(parent_class: parent_class)

          classes.each do |klass|
            class_name = klass.to_s

            if (!class_name.include?(class_prefix))
              next
            end

            result << klass
          end

          result
        end

        def load_classes(parent_class:)
          result = []

          ObjectSpace.each_object do |klass|
            next unless Module === klass

            class_name = klass.to_s

            if class_name.include?("#")
              next
            end

            result << klass if parent_class >= klass
          end

          result
        end
      end
    end
  end
end
