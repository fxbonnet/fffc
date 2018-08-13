package com.octo.fffc.rest;

import com.octo.fffc.exceptions.AccessDeniedException;
import com.octo.fffc.model.InputRequestDto;
import com.octo.fffc.service.FileFormatService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author alanterriaga
 * @project FFFC
 */
@RunWith(MockitoJUnitRunner.class)
public class FileFormatControllerTest {

    @Mock
    FileFormatService formatService;

    @InjectMocks
    FileFormatController controller;

    /**
     * Scenario: Request information not valid
     * Expected: Bad request
     */
    @Test
    public void test_formatFile_requestNotValid(){

        ResponseEntity responseEntity = controller.formatFile(new InputRequestDto(), new MockHttpServletResponse());

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    /**
     * Scenario: Request not authorized
     * Expectred: HttpStatus.UNAUTHORIZED
     * @throws Exception
     */
    @Test
    public void test_formatFile_accessDeniedException() throws Exception {

        when(formatService.formatAndCreateFile(any())).thenThrow(AccessDeniedException.class);

        InputRequestDto inputRequestDto = new InputRequestDto();
        inputRequestDto.setDataFileName("datafile.txt");
        inputRequestDto.setMetaDataFileName("metadata.txt");

        ResponseEntity responseEntity = controller.formatFile(inputRequestDto, new MockHttpServletResponse());

        Assert.assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    }

    /**
     * Scenario: Error while generate the file
     * Expected: HttpStatus.BAD_REQUEST
     * @throws Exception
     */
    @Test
    public void test_formatFile_fileNotGenerated() throws Exception {

        InputRequestDto inputRequestDto = new InputRequestDto();
        inputRequestDto.setDataFileName("datafile.txt");
        inputRequestDto.setMetaDataFileName("metadata.txt");

        when(formatService.formatAndCreateFile(inputRequestDto)).thenReturn(false);

        ResponseEntity responseEntity = controller.formatFile(inputRequestDto, new MockHttpServletResponse());

        Assert.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    /**
     * Scenario: File generated
     * Expected: HttpStatus.OK
     * @throws Exception
     */
    @Test
    public void test_formatFile_success() throws Exception {

        InputRequestDto inputRequestDto = new InputRequestDto();
        inputRequestDto.setDataFileName("datafile.txt");
        inputRequestDto.setMetaDataFileName("metadata.txt");

        when(formatService.formatAndCreateFile(inputRequestDto)).thenReturn(true);

        ResponseEntity responseEntity = controller.formatFile(inputRequestDto, new MockHttpServletResponse());

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }


}
