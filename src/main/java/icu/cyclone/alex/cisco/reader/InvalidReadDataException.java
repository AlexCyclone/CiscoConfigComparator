package icu.cyclone.alex.cisco.reader;

public class InvalidReadDataException extends Exception {

    private String message;

    public InvalidReadDataException() {
        setMessage("Data could not be read");
    }

    public InvalidReadDataException(String message) {
        setMessage(message);
    }

    private void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
