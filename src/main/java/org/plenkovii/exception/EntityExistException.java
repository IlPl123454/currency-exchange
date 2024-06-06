package org.plenkovii.exception;

public class EntityExistException extends RuntimeException{
    public EntityExistException(String message) {
        super(message);
    }
    public EntityExistException() {
    }
}
