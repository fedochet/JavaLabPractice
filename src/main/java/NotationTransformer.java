import java.util.*;

/**
 * Created by roman on 24.08.2016.
 */
public class NotationTransformer {

    private static int getOperationPriority(String op) {
        switch (op) {
            case "*":
            case "/":
                return 3;
            case "+":
            case "-":
                return 2;
            case "(":
            case ")":
                return 1;
            default:
                throw new IllegalArgumentException("Illegal operation " + op);
        }
    }

//    static class OperationComparator implements Comparator<String> {
//        @Override
//        public int compare(String o1, String o2) {
//            return getOperationPriority(o1)-getOperationPriority(o2);
//        }
//    }

    public List<String> transform(List<String> equation) {
        List<String> result = new ArrayList<>();

        Deque<String> operationsStack = new ArrayDeque<>();

        int numberOfOperators = 0;
        int numberOfNumbers = 0;

        for (String elem: equation) {
            switch (elem) {
                case "*":
                case "/":
                case "+":
                case "-":
                    numberOfOperators++;
                    while (!operationsStack.isEmpty() &&
                            getOperationPriority(elem)<=getOperationPriority(operationsStack.peekFirst())) {
                        result.add(operationsStack.removeFirst());
                    }
                    operationsStack.addFirst(elem);
                    break;
                case "(":
                    operationsStack.addFirst(elem);
                    break;
                case ")":
                    try {
                        while (!operationsStack.getFirst().equals("(")) {
                            result.add(operationsStack.removeFirst());
                        }
                        operationsStack.removeFirst();
                        break;
                    } catch (NoSuchElementException e) {
                        throw new IllegalArgumentException("Parenthesis are not sound");
                    }
                default:
                    numberOfNumbers++;
                    result.add(elem);
            }
        }

        for (String operation: operationsStack) {
            result.add(operation);
        }

        if (!equation.isEmpty() && numberOfOperators+1!=numberOfNumbers) {
            throw new IllegalArgumentException("Illegal equation");
        }

        return result;
    }
}
