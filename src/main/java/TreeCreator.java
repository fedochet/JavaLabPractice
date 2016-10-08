import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by roman on 24.08.2016.
 */
public class TreeCreator {

    public Calculator.TreeNode create(List<String> equation) {
        if (equation.isEmpty()) throw new IllegalArgumentException("Empty equation!");

        Deque<Calculator.TreeNode> treeStack = new ArrayDeque<>();

        try {
            for (String elem : equation) {
                switch (elem) {
                    case "*": {
                        Calculator.TreeNode right = treeStack.removeLast();
                        Calculator.TreeNode left = treeStack.removeLast();
                        treeStack.addLast(new Calculator.MultOp(left, right));
                        break;
                    }
                    case "/": {
                        Calculator.TreeNode right = treeStack.removeLast();
                        Calculator.TreeNode left = treeStack.removeLast();
                        treeStack.addLast(new Calculator.DivOp(left, right));
                        break;
                    }
                    case "+": {
                        Calculator.TreeNode right = treeStack.removeLast();
                        Calculator.TreeNode left = treeStack.removeLast();
                        treeStack.addLast(new Calculator.PlusOp(left, right));
                        break;
                    }
                    case "-": {
                        Calculator.TreeNode right = treeStack.removeLast();
                        Calculator.TreeNode left = treeStack.removeLast();
                        treeStack.addLast(new Calculator.MinusOp(left, right));
                        break;
                    }

                    default:
                        treeStack.addLast(new Calculator.Leaf(Double.parseDouble(elem)));
                }
            }
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Illegal equation!");
        }

        return treeStack.getFirst();
    }
}
