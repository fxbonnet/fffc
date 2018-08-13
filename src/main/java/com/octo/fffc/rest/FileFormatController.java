package com.octo.fffc.rest;

import com.octo.fffc.exceptions.AccessDeniedException;
import com.octo.fffc.model.InputRequestDto;
import com.octo.fffc.security.Secured;
import com.octo.fffc.service.FileFormatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author alanterriaga
 * @project fffc
 */
@RestController
@RequestMapping("/fffc")
@Api(value="fffc", description = "Octo Fixed File Format Converter")
public class FileFormatController {

    @Autowired
    FileFormatService fileFormatService;

    @ApiOperation(value ="Format Input File", response = ResponseEntity.class)
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Successfully converted file"),
            @ApiResponse(code = 400, message = "Error while generating File"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "You are not authorized to view the resource")
    })
    @Secured
    @RequestMapping(value="/formatFile", method = RequestMethod.POST)
    public ResponseEntity<String> formatFile(@RequestBody InputRequestDto inputRequestDto, HttpServletResponse response) {

        try {
            if (inputRequestDto == null || StringUtils.isEmpty(inputRequestDto.getDataFileName()) ||
                    StringUtils.isEmpty(inputRequestDto.getMetaDataFileName())) {
                return ResponseEntity.badRequest().build();
            }

            if(fileFormatService.formatAndCreateFile(inputRequestDto)){
                ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.OK).build();
                response.getWriter().append("File generated");
                response.setContentType("text/plain");
                return responseEntity;
            }
            else {
                ResponseEntity responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                response.getWriter().append("Error while generating File");
                response.setContentType("text/plain");
                return responseEntity;
            }
        }
        catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}
