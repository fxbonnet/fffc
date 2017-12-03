package com.project.utility.test.utils;

import com.project.utility.utils.StringUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by rssridh on 12/4/17.
 */
public class StringUtilsTest {
    @Test
    public void testConvertDateToYearFormat() {
        Assert.assertEquals("output.csv", StringUtils.converFileExtensions("output", "csv"));
        Assert.assertEquals("output.csv", StringUtils.converFileExtensions("output.txt", "csv"));
        Assert.assertEquals("output.xls", StringUtils.converFileExtensions("output", "xls"));
    }

    @Test
    public void testFormatString() {
        Assert.assertEquals("something", StringUtils.formatString("something"));
        Assert.assertEquals("\"something, and more\"", StringUtils.formatString("something, and more"));
        Assert.assertEquals("\",,,,,\"", StringUtils.formatString(",,,,,"));
    }
}
