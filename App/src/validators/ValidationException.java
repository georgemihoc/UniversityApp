package validators;

public class ValidationException extends RuntimeException {
    public ValidationException(String ex) {
        super(ex);
        //System.out.println(ex);
        //throw new ValidationException("Invalid");
    }
}
