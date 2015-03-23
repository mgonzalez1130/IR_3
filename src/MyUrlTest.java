import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MyUrlTest {

    MyUrl url1 = new MyUrl("www.google.com", 1);
    MyUrl url2 = new MyUrl("www.facebook.com", 2);
    MyUrl url3 = new MyUrl("www.yahoo.com", 3);
    MyUrl url4 = new MyUrl("www.yahoo.com", 1);

    @Test
    public void testEqualsObject() {
        assertTrue(url3.equals(url3));
        assertTrue(url3.equals(url4));
    }

}
