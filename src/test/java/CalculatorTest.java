import exceptions.DivisionByZeroException;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by student on 22/08/16.
 */

public class CalculatorTest {
    @Test
    public void leafTest() throws Exception {
        Calculator.Leaf leaf = new Calculator.Leaf(12);
        assertEquals(leaf.compute(), 12, 0);
    }

    @Test
    public void leafEqualsTest() {
        Calculator.Leaf leafOne = new Calculator.Leaf(10);
        Calculator.Leaf leafTwo = new Calculator.Leaf(20);

        assertNotEquals(leafOne, null);
        assertNotEquals(leafOne, leafTwo);

        assertEquals(leafOne, leafOne);
        assertEquals(leafOne, new Calculator.Leaf(10));

        assertEquals(leafTwo, leafTwo);
        assertEquals(leafTwo, new Calculator.Leaf(20));
    }

    @Test
    public void operationEqualsTest() {
        Calculator.Leaf leafOne = new Calculator.Leaf(10);
        Calculator.Leaf leafTwo = new Calculator.Leaf(20);

        Calculator.PlusOp plusOp = new Calculator.PlusOp(leafOne, leafTwo);
        Calculator.MinusOp minusOp = new Calculator.MinusOp(leafOne, leafTwo);
        Calculator.MultOp multOp = new Calculator.MultOp(leafOne, leafTwo);
        Calculator.DivOp divOp = new Calculator.DivOp(leafOne, leafTwo);

        assertNotEquals(plusOp, null);
        assertNotEquals(minusOp, null);
        assertNotEquals(multOp, null);
        assertNotEquals(divOp, null);

        assertNotEquals(plusOp, minusOp);
        assertNotEquals(minusOp, multOp);
        assertNotEquals(multOp, divOp);
        assertNotEquals(divOp, plusOp);

        assertEquals(plusOp, plusOp);
        assertEquals(plusOp, new Calculator.PlusOp(leafOne, leafTwo));

        assertEquals(multOp, multOp);
        assertEquals(multOp, new Calculator.MultOp(leafOne, leafTwo));

        assertEquals(minusOp, minusOp);
        assertEquals(minusOp, new Calculator.MinusOp(leafOne, leafTwo));

        assertEquals(divOp, divOp);
        assertEquals(divOp, new Calculator.DivOp(leafOne, leafTwo));
    }

    @Test
    public void plusOpTest() {

        Calculator.TreeNode a = new Calculator.Leaf(10);
        Calculator.TreeNode b = new Calculator.Leaf(20);

        Calculator.PlusOp plusOp = new Calculator.PlusOp(a, b);
        assertEquals(plusOp.compute(), 30, 0);
    }

    @Test
    public void minusOpTest() {
        Calculator.TreeNode a = new Calculator.Leaf(10);
        Calculator.TreeNode b = new Calculator.Leaf(20);

        Calculator.MinusOp minusOp = new Calculator.MinusOp(a, b);
        assertEquals(minusOp.compute(), -10, 0);
    }

    @Test
    public void multTest() {
        Calculator.TreeNode a = new Calculator.Leaf(10);
        Calculator.TreeNode b = new Calculator.Leaf(20);

        Calculator.MultOp multOp = new Calculator.MultOp(a, b);
        assertEquals(multOp.compute(), 200, 0);
    }

    @Test
    public void divCorrectTest() {
        Calculator.TreeNode a = new Calculator.Leaf(10);
        Calculator.TreeNode b = new Calculator.Leaf(20);

        Calculator.DivOp divOp = new Calculator.DivOp(a, b);
        assertEquals(divOp.compute(), 0.5, 0);
    }

    @Test
    public void divisionByZeroExceptionTest() {
        Calculator.TreeNode a = new Calculator.Leaf(10);
        Calculator.TreeNode b = new Calculator.Leaf(0);

        Calculator.DivOp divOp = new Calculator.DivOp(a, b);

        try {
            divOp.compute();
            fail("You cannot divide by zero");
        } catch (DivisionByZeroException e) {
            assertEquals("You cannot divide by zero", e.getMessage());
        }
    }

    private Calculator.Parser parser = new Calculator.Parser();


    @Test
    public void parseSimpleNumber() {
        String equation = "5";

        Calculator.TreeNode node = parser.parse(equation);
        assertEquals(node, new Calculator.Leaf(5));
    }

    @Test
    @Ignore
    public void parseSimpleSum() {
        String equation = "5+10";

        assertEquals(parser.parse(equation), new Calculator.PlusOp(new Calculator.Leaf(5), new Calculator.Leaf(10)));
    }

}