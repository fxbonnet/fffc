package com.assignment.fffc.processors;

import com.assignment.fffc.formats.ColumnFormatProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class CSVDataProcessorTest {

    @Mock
    private ColumnFormatProvider columnFormatProvider;

    @InjectMocks
    private CSVDataProcessor csvDataProcessor;


    @Test
    public void shouldDoSomething() {
        //csvDataProcessor.process("1970-02-06John,y         Smith           81.5",);
    }
}