package octo;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ExpandedParameterBuilderPlugin;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.readers.parameter.ExpandedParameterBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static com.google.common.base.Predicates.or;
import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.builders.PathSelectors.regex;


/**
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Autowired
    private TypeResolver typeResolver;

    @Bean
    public ExpandedParameterBuilderPlugin expandedParameterBuilderPlugin() {
        return new ExpandedParameterBuilder(typeResolver);
    }

    @Bean
    public Docket swaggerSpringMvcPlugin() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("fixed-file-format-converter-api")
                .apiInfo(apiInfo())
                .select()
                .paths(paths()).build()
                .directModelSubstitute(LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(LocalDateTime.class, java.util.Date.class)
                .useDefaultResponseMessages(false)
                .globalOperationParameters(new ArrayList<>())
                .globalResponseMessage(RequestMethod.GET, getResponseMessages(RequestMethod.GET))
                .globalResponseMessage(RequestMethod.POST, getResponseMessages(RequestMethod.POST))
                .globalResponseMessage(RequestMethod.DELETE, getResponseMessages(RequestMethod.DELETE))
                .globalResponseMessage(RequestMethod.PATCH, getResponseMessages(RequestMethod.PATCH))
                .globalResponseMessage(RequestMethod.PUT, getResponseMessages(RequestMethod.PUT));
    }


    private ArrayList<ResponseMessage> getResponseMessages(RequestMethod requestMethod) {
        // common errors
        ArrayList<ResponseMessage> responseMessages = newArrayList(
                new ResponseMessageBuilder()
                        .code(400)
                        .message("Bad request")
                        .responseModel(new ModelRef("Error"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(401)
                        .message("Unauthorized")
                        .responseModel(new ModelRef("Error"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(404)
                        .message("Not Found")
                        .responseModel(new ModelRef("Error"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(415)
                        .message("Unsupported Media Type")
                        .responseModel(new ModelRef("Error"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(500)
                        .message("Internal service error")
                        .responseModel(new ModelRef("Error"))
                        .build());
        if (requestMethod == RequestMethod.POST) {
            responseMessages.add(new ResponseMessageBuilder()
                    .code(201)
                    .message("Created")
                    .build());
        }
        if (requestMethod == RequestMethod.GET) {
            responseMessages.add(new ResponseMessageBuilder()
                    .code(200)
                    .message("OK")
                    .build());
        }
        return responseMessages;
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "Fixed File Format Converter API",
                "Convert fixed file format files to csv ",
                "1.0",
                "",
                getContactDetails(),
                "",
                "",
                new ArrayList<>());
        return apiInfo;
    }

    private Contact getContactDetails() {
        return new Contact("", "", "");
    }


    private Predicate<String> paths() {
        return or(
                regex("/convert.*")
        );
    }


}
