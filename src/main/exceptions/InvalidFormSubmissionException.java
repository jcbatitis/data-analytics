package main.exceptions;

public class InvalidFormSubmissionException extends Exception{
    public InvalidFormSubmissionException(String message) {
        super(message);
    }
}
