import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.Test;

import java.util.Collections;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by roman on 24.08.2016.
 */
public class TreeCreatorTest {
    private TreeCreator treeCreator = new TreeCreator();


    @Test
    public void parseSimpleNumber() {
        Calculator.TreeNode node = treeCreator.create(asList("5"));
        assertEquals(node, new Calculator.Leaf(5));
    }

    @Test
    public void parseSimpleEquations() {
        assertEquals(treeCreator.create(asList("5","10","+")), new Calculator.PlusOp(new Calculator.Leaf(5), new Calculator.Leaf(10)));
        assertEquals(treeCreator.create(asList("1","2","3","*","+")),
                new Calculator.PlusOp(
                        new Calculator.Leaf(1),
                        new Calculator.MultOp(
                                new Calculator.Leaf(2),
                                new Calculator.Leaf(3)
                        )
                ));
    }

    @Test
    public void emptyEquation() {
        try {
            treeCreator.create(Collections.emptyList());
            fail("Cannot create tree from empty list!");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Empty equation!"));
        }
    }

    @Ignore
    @Test
    public void illegalEquation() {
        try {
            treeCreator.create(asList("+"));
            fail("Cannot create tree from illegal equation!");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("Illegal equation!"));
        }
    }
}