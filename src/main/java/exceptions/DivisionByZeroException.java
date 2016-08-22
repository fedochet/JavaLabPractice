package exceptions;

/**
 * Created by student on 22/08/16.
 */
public class DivisionByZeroException extends RuntimeException {
    @Override
    public String getMessage() {
        return "You cannot divide by zero";
    }
}
