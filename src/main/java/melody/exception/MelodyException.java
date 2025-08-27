package melody.exception;

/**
 * Represents an exception specific to Melody application operations.
 * Thrown when an error occurs during task processing or data handling.
 */

public class MelodyException extends Exception{
    /**
     * Constructs a new MelodyException with the specified detail message.
     *
     * @param message the detail message explaining the error
     */
    public MelodyException(String message) {
        super("  ERROR: " + message);
    }
}
