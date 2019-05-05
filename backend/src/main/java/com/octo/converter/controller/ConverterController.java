package com.octo.converter.controller;

import com.octo.converter.config.Configs;
import com.octo.converter.service.ConverterException;
import com.octo.converter.service.ConverterService;
import com.octo.converter.service.util.Constants;
import com.octo.converter.service.util.FileUtils;
import com.octo.converter.service.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/convert")
public class ConverterController {
    private final ConverterService converterService;
  @Autowired
  private Configs configs;
    @Autowired
    private Validator validator;
    @Autowired
    public ConverterController(ConverterService converterService) {
        this.converterService = converterService;
    }

@PostMapping("/fffToCsv")
    public ResponseEntity<Resource> convertFffToCsv(@RequestParam("files") MultipartFile[] inputFiles,HttpServletResponse response) throws IOException {
    List<File> files=new ArrayList<>();
    for(MultipartFile uploadedFile : inputFiles) {
        File file = File.createTempFile(configs.TMP_FOLDER+ uploadedFile.getOriginalFilename(),FileUtils.getExtension(uploadedFile.getOriginalFilename()));
        uploadedFile.transferTo(file);
        files.add(file);
        if(files.size()==2) break; //to ignore extra files if more than 2 files are sent
    }
    try {
        validator.validateInput(files);
    } catch (ConverterException e) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write(e.getMessage());
        return null;
    }
    File resultFile=null;
    if(files.get(0).getName().contains(Constants.CSV_EXTENSION))
    {
        //System.out.println(files.get(0).getName());
        try {
            resultFile=converterService.convert(files.get(1),files.get(0));
        } catch (ConverterException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(e.getMessage());
            return null;
        }
    }else{
        try {
            resultFile=converterService.convert(files.get(0),files.get(1));
        } catch (ConverterException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(e.getMessage());
            return null;
        }
    }
    return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename="+configs.RESULT_FILE_NAME).header(HttpHeaders.CONTENT_TYPE,"application/csv").body(new FileSystemResource(resultFile));
}
}
