import exceptions.DivisionByZeroException;

/**
 * Created by student on 22/08/16.
 */
public class Calculator {
    public static final double EPSILON = 0.0001;
    private final LexicalParser lexicalParser;
    private final NotationTransformer transformer;
    private final TreeCreator treeCreator;

    public Calculator(LexicalParser lexicalParser, NotationTransformer transformer, TreeCreator treeCreator) {
        this.lexicalParser = lexicalParser;
        this.transformer = transformer;
        this.treeCreator = treeCreator;
    }

    public double calculate(String equation) {
        return treeCreator.create((transformer.transform(lexicalParser.parse(equation)))).compute();
    }

    static abstract class TreeNode {
        abstract double compute();

        protected boolean doubleEquals(double a, double b) {
            return Math.abs(a - b) <= EPSILON;
        }
    }

    static class Leaf extends TreeNode {
        private final double value;

        Leaf(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj==null || !(obj instanceof Leaf)) return false;

            return obj==this || doubleEquals(((Leaf)obj).value, this.value);
        }

        @Override
        public double compute() {
            return value;
        }
    }

    static abstract class Operator extends TreeNode {
        @Override
        public double compute() {
            return operate(left.compute(), right.compute());
        }

        abstract protected double operate(double left, double right);

        private final TreeNode left;
        private final TreeNode right;

        public Operator(TreeNode left, TreeNode right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj==null || !(obj.getClass().equals(this.getClass()))) return false;

            return (this == obj) || ((this.left.equals(((Operator) obj).left)) && this.right.equals(((Operator) obj).right));
        }
    }

    static class PlusOp extends Operator {

        PlusOp(TreeNode left, TreeNode right) {
           super(left, right);
        }

        @Override
        protected double operate(double left, double right) {
            return left+right;
        }
    }

    static class MinusOp extends Operator {

        MinusOp(TreeNode left, TreeNode right) {
            super(left, right);

        }

        @Override
        protected double operate(double left, double right) {
            return left-right;
        }
    }

    static class MultOp extends Operator {
        MultOp(TreeNode left, TreeNode right) {
            super(left, right);
        }

        @Override
        protected double operate(double left, double right) {
            return left*right;
        }

    }

    static class DivOp extends Operator {
        DivOp(TreeNode left, TreeNode right) {
            super(left, right);
        }

        @Override
        protected double operate(double left, double right) {
            if (doubleEquals(0, right)) {
                throw new DivisionByZeroException();
            }
            return left/right;
        }
    }
}
