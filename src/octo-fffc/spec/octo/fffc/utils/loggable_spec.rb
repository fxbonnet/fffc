require 'octo/fffc/utils/loggable'

include Octo::Fffc::Utils

RSpec.describe Octo::Fffc::Utils::Loggable do
  
    class DummyService  
        include Octo::Fffc::Utils::Loggable

        def log_info(value)
            log.info(value)
        end

        def log_debug(value)
            log.debug(value)
        end

        def log_warn(value)
            log.warn(value)
        end

        def log_error(value)
            log.error(value)
        end
    end

    context '.initialize' do
        it "can initialize" do
            service = DummyService.new
            
            expect(service.log).not_to eq(nil)
        end
    end

    context '.methods' do
        it "can log messages" do
            service = DummyService.new
            
            expect {
                service.log_info("test")
                service.log_debug("test")
                service.log_warn("test")
                service.log_error("test")
            }.not_to raise_error
        end

        it "can toggle debug level" do
            service = DummyService.new
            
            service.set_verbose()
            service.log_debug("test")

            service.set_info
            service.log_debug("test")
        end
    end

    context '.metadata' do
        it "colors" do
            
            expect(Loggable._white).to      eq(37)
            expect(Loggable._red).to        eq(31)
            expect(Loggable._green).to      eq(32)
            expect(Loggable._yellow).to     eq(33)
            expect(Loggable._blue).to       eq(34)
            expect(Loggable._pink).to       eq(35)
            expect(Loggable._light_blue).to eq(36)
            expect(Loggable._gray).to       eq(37)
            
        end
    end

end  