import exceptions.DivisionByZeroException;

/**
 * Created by student on 22/08/16.
 */
public class Calculator {
    public static final double EPSILON = 0.0001;

    static class Parser {

        public TreeNode parse(String equation) {
            return new Leaf(5);
        }
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

            return doubleEquals(((Leaf)obj).value, this.value);
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