package com.assignment.fffc.controllers;

import com.assignment.fffc.constants.Constants;
import com.assignment.fffc.model.ConvertRequest;
import com.assignment.fffc.model.ConvertResponse;
import com.assignment.fffc.services.FormatConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
@Slf4j
public class FormatConverterController {

    private FormatConverter converter;

    @Autowired
    public FormatConverterController(FormatConverter converter) {
        this.converter = converter;
    }

    @RequestMapping(value = "/convert", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ConvertResponse> convertFormat(@RequestBody ConvertRequest request) {

        ConvertResponse response = new ConvertResponse();
        try {
            File convert = converter.convert(request.getMetadataFileLocation(), request.getInputFileLocation(), request.getOutputFileName(), request.getFormatType());
            response.setOutputFileName(convert.getAbsolutePath());
            response.setStatus(Constants.SUCCESS);
        } catch (Exception ex) {
            response.setException(ex.toString());
            response.setStatus(Constants.FAILED);
            log.info(Constants.FAILED, ex);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
