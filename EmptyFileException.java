import java.io.IOException;
public class EmptyFileException extends IOException {
    public EmptyFileException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "EmptyFileException: " + getMessage();
    }
}