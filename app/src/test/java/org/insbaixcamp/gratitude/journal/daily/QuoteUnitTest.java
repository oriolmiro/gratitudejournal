package org.insbaixcamp.gratitude.journal.daily;
import org.insbaixcamp.gratitude.journal.daily.model.Quote;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
public class QuoteUnitTest {

    private Quote quote;

    @Before
    public void setUp() {
        quote = new Quote("John Doe", "This is a test quote.");
    }

    @Test
    public void testConstructor() {
        assertEquals("John Doe", quote.getAuthor());
        assertEquals("This is a test quote.", quote.getPhrase());
    }

    @Test
    public void testSetAuthor() {
        quote.setAuthor("Jane Doe");
        assertEquals("Jane Doe", quote.getAuthor());
    }

    @Test
    public void testSetPhrase() {
        quote.setPhrase("Another test quote.");
        assertEquals("Another test quote.", quote.getPhrase());
    }

    @Test
    public void testToString() {
        String expected = "Quote{author='John Doe', phrase='This is a test quote.'}";
        assertEquals(expected, quote.toString());
    }
}
