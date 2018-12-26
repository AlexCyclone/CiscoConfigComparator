package icu.cyclone.alex.cisco;

public class InvalidConfigFormatException extends Exception {
    private String message;

    public InvalidConfigFormatException() {
        setMessage("Unsupported signature was found");
    }

    public InvalidConfigFormatException(String message) {
        setMessage(message);
    }

    private void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
