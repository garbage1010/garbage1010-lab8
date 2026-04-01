public class InvalidStopwordException extends Exception {
    public InvalidStopwordException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "InvalidStopwordException: " + getMessage();
    }
}