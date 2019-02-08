package com.assignment.fffc.controllers;

import com.assignment.fffc.model.ConvertRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FormatConverterControllerTest {

    TestRestTemplate testRestTemplate = new TestRestTemplate();

    @Test
    public void convert() throws Exception{
        testRestTemplate.postForLocation(new URI("http://localhost:8080/convert"),new ConvertRequest());
    }
}