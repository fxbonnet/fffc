package com.assignment.fffc.controllers;

import com.assignment.fffc.model.ConvertRequest;
import com.assignment.fffc.services.FormatConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.File;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(FormatConverterController.class)
public class FormatConverterControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    FormatConverter converter;

    @Test
    public void postConvertFile() throws Exception {

        when(converter.convert(anyString(),anyString(),anyString(),anyString())).thenReturn(new File("src/test/resources/files/sample-output.txt"));
        mvc.perform(MockMvcRequestBuilders
                .post("/convert")
        .content(asJsonString(new ConvertRequest("src/test/resources/files/data.txt",
                "src/test/resources/files/metadata.txt",
                "src/test/resources/files/output.txt",
                "csv")))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("converted"));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}