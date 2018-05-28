package octo.controller;

import io.swagger.annotations.ApiOperation;
import octo.ApplicationConstants;
import octo.exception.InputFileException;
import octo.exception.MetadataFileException;
import octo.exception.OutputFileException;
import octo.model.ColumnMetadata;
import octo.service.FffcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class FffcController {

    private static final Logger log = LoggerFactory.getLogger(FffcController.class);
    private FffcService fffcService;

    @Autowired
    public FffcController(FffcService fffcService) {
        this.fffcService = fffcService;
    }


    @ApiOperation(value = "Convert the input file to csv file based on the given column metadata",
            notes = "Convert the input file to csv file based on the given column metadata",
            tags = ApplicationConstants.SWAGGER_TAG
    )
    @RequestMapping(value = "/convert", method = RequestMethod.GET)
    public void convertToCsv(@RequestParam String inputFilePath,
                             @RequestParam String metadataFilePath,
                             @RequestParam String outputFilePath)
            throws InputFileException, OutputFileException, MetadataFileException {
        log.debug("Received the request to start file conversion");
        List<ColumnMetadata> columnMetadataList = fffcService.getColumnMetadata(metadataFilePath);
        List<String> formattedRows = fffcService.readInputFile(inputFilePath, columnMetadataList);
        fffcService.writeToCsv(outputFilePath, formattedRows);

    }

}
