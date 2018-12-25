package icu.cyclone.alex.utils;

public class InvalidFileFormatException extends RuntimeException {

    private String message;

    public InvalidFileFormatException() {
        setMessage("Unsupported signature was found");
    }

    public InvalidFileFormatException(String message) {
        setMessage(message);
    }

    private void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
