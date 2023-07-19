package dev.gracie.elearn.exceptions;

public class EmailAlreadyUsedException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public EmailAlreadyUsedException () {
        super("email already used!");
    }
}
