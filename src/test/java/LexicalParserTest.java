import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by roman on 23.08.2016.
 */
public class LexicalParserTest {

    LexicalParser parser = new LexicalParser();

    @Test
    public void testEmptyEquation() {
        assertTrue(parser.parse("").isEmpty());
    }

    @Test
    public void testSingleNumber() {
        assertThat(parser.parse("1.2345"), is(Arrays.asList("1.2345")));
    }

    @Test
    public void testSimpleEquation() {
        assertThat(parser.parse("1.234+4.567"), is(Arrays.asList("1.234","+","4.567")));
    }

    @Test
    public void testComplexEquation() {
        assertThat(parser.parse("(1.25 + 16)/28 - 36.28 * (44-31*(27.19/3) )"),
                is(Arrays.asList("(","1.25","+","16",")","/","28","-","36.28","*",
                        "(","44","-","31","*","(","27.19","/","3",")",")")));
    }

}