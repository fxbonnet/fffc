import octa.App;
import octa.Header;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static junit.framework.TestCase.assertEquals;

public class DataConverterTest
{
    @Test
    public void testDataConverterSuccess() {
        String[] result = App.dataConverter.apply(getLineItem(), getHeaders());
        assertEquals("01/01/1970", result[0]);
        assertEquals("John", result[1]);
        assertEquals("Smith", result[2]);
        assertEquals("81.5", result[3]);
    }

    @Test
    public void testDataConverterSuccess1() {
        String[] result = App.dataConverter.apply(getLineItem1(), getHeaders1());
        assertEquals("A", result[0]);
        assertEquals("12", result[1]);
        assertEquals("B", result[2]);
    }

    @Test(expected = Exception.class)
    public void testDataConverterError() {
        String[] result = App.dataConverter.apply(getLineItem(), null);
    }

    private String getLineItem() {
        return "1970-01-01John           Smith           81.5";
    }

    private String getLineItem1() {
        return "A12B";
    }

    private List<Header> getHeaders1() {
        List<Header> headers = new ArrayList<>();
        headers.add(new Header("col1",1,"string"));
        headers.add(new Header("col2",2,"numeric"));
        headers.add(new Header("col3",1,"string"));
        return headers;
    }

    private List<Header> getHeaders() {
        List<Header> headers = new ArrayList<>();
        headers.add(new Header("Birth date",10,"date"));
        headers.add(new Header("fname",15,"string"));
        headers.add(new Header("lname",15,"string"));
        headers.add(new Header("wt",5,"numeric"));
        return headers;
    }
}
