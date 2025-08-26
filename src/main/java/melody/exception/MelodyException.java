package melody.exception;

public class MelodyException extends Exception{

    public MelodyException(String message) {
        super("  ERROR: " + message);
    }
}
