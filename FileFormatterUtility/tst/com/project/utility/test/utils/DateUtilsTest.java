package com.project.utility.test.utils;

import com.project.utility.exception.IncorrectDataFormatException;
import org.junit.Test;
import com.project.utility.utils.DateUtils;
import org.junit.Assert;

/**
 * Created by rssridh on 12/3/17.
 */
public class DateUtilsTest {

    @Test
    public void testConvertDateToYearFormat() throws IncorrectDataFormatException {
        Assert.assertEquals("31-05-2017", DateUtils.convertDateToYearFormat("2017-05-31"));
        Assert.assertEquals("05-05-2017", DateUtils.convertDateToYearFormat("2017-05-05"));
    }

    @Test(expected = IncorrectDataFormatException.class)
    public void testConvertInCorrectDateToYearFormat() throws IncorrectDataFormatException {
        Assert.assertEquals("31-05-2017", DateUtils.convertDateToYearFormat("2017-05-1"));
        Assert.assertEquals("31-05-2017", DateUtils.convertDateToYearFormat(""));

    }

}
