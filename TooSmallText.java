public class TooSmallText extends Exception {
    public TooSmallText(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "TooSmallText: " + getMessage();
    }
}