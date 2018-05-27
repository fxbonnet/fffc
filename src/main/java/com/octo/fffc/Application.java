package com.octo.fffc;

import com.octo.fffc.converter.Configurator;
import com.octo.fffc.converter.Converter;
import com.octo.fffc.exception.InvalidInputException;
import com.octo.fffc.converter.InputArguments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.util.List;

@SpringBootApplication
public class Application implements ApplicationRunner {

    private final Converter converter;
    private final Configurator config;
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public Application(Converter converter,
                       Configurator config) {
        this.converter = converter;
        this.config = config;
    }

    @Override
    public void run(ApplicationArguments args) {
        List<String> inputs = args.getNonOptionArgs();
        System.out.println("Logs for the application could be found here : " + getAbsLocation(config.getLogDirectory()));
        try {
            validateInputArguments(inputs);
            InputArguments arguments = new InputArguments(inputs.get(0), inputs.get(1), inputs.get(2));
            logger.info("Transforming the input file : {}", arguments.getInputFile());
            converter.convert(arguments);
        } catch (InvalidInputException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.debug("", e);
        }
    }

    private void validateInputArguments(List<String> inputs) throws InvalidInputException {
        if (inputs.size() < 2) {
            StringBuilder msg = new StringBuilder();
            msg.append("Please pass atleast 3 arguments : ");
            msg.append(System.lineSeparator());
            msg.append("Usage : ");
            msg.append(System.lineSeparator());
            msg.append("java -jar file-format-converter.*.jar <inputFile> <metadataFile> <outputFile>");
            msg.append(System.lineSeparator());
            throw new InvalidInputException(msg.toString());
        }
    }

    private String getAbsLocation(String file) {
        return new File(file).getAbsolutePath();
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.setLogStartupInfo(false);
        app.run(args);
    }
}
