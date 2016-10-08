import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by roman on 24.08.2016.
 */

public class NotationTransformerTest {

    private List<String> parse(String string) {
        LexicalParser parser = new LexicalParser();

        return parser.parse(string);
    }

//    @Parameterized.Parameters
//    public static Collection<Object[]> data() {
//        return Arrays.asList(new Object[][] {
//                {"*","*",0}, {"/","/",0}, {"*","/",0},
//                {"+","+",0}, {"-","-", 0}, {"+","-",0},
//                {"(","(",0}, {")",")",0}, {"(",")",0},
//                {"*","+",1}, {"*","-",1}, {"/","+",1}, {"/","-",1},
//                {"*","(",2}, {"*",")",2}, {"/","(",2}, {"/",")",2},
//                {"+","(",1}, {"+",")",1}, {"-","(",1}, {"-",")",1}
//        });
//    }
//
//    @Parameterized.Parameter(0)
//    public String left;
//
//    @Parameterized.Parameter(1)
//    public String right;
//
//    @Parameterized.Parameter(2)
//    public int result;
//
//    @Test
//    public void operationComparatorTest() {
//        NotationTransformer.OperationComparator comparator = new NotationTransformer.OperationComparator();
//        assertThat(comparator.compare(left, right), is(result));
//        assertThat(comparator.compare(right, left), is(-result));
//
//    }

    private NotationTransformer transformer = new NotationTransformer();

    @Test
    public void simpleEquations() {
        assertThat(transformer.transform(parse("1")), is(asList("1")));

        assertThat(transformer.transform(parse("1+2")),
                is(asList("1", "2", "+")));

        assertThat(transformer.transform(parse("1+2*3")),
                is(asList("1", "2", "3", "*", "+")));

        assertThat(transformer.transform(parse("1*2-3/4")),
                is(asList("1", "2", "*", "3", "4", "/", "-")));
    }

    @Test
    public void simpleParenthesis() {
        assertThat(transformer.transform(parse("(1+2)")),
                is(asList("1", "2", "+")));

        assertThat(transformer.transform(asList("(", "1", "+", "2", ")", "*", "3")),
                is(asList("1", "2", "+", "3", "*")));
    }

    @Test
    public void complexEquation() {
        assertThat(transformer.transform(parse("3 + 4 * 2 / (1 - 5)")),
                is(asList("3", "4", "2", "*", "1", "5", "-", "/", "+")));
    }

    @Test
    public void illegalEquation() {
        try {
            transformer.transform(parse("3+4-6+*3"));
            fail("Such expression cannot be transformed!");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Illegal equation"));
        }
    }

    @Test
    public void illegalParenthesis() {
        try {
            transformer.transform(parse("1+2*(3+4))-5"));
            fail("Such expression cannot be transformed!");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Parenthesis are not sound"));
        }
    }

    @Test
    public void emptyListTest() {
        assertThat(transformer.transform(Collections.emptyList()), is(Collections.emptyList()));
    }

}