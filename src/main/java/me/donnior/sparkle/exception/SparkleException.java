package me.donnior.sparkle.exception;

public class SparkleException extends RuntimeException{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public SparkleException(String message) {
        super(message);
    }

    public SparkleException(String messageTemplate, Object... args) {
        super(String.format(messageTemplate, args));
    }
    public static void throwIf(boolean bool, String message) {
        if (bool) {
            throw new SparkleException(message);
        }
    }

    public static void throwIf(boolean bool, String messageTemplate, Object... args) {
        if (bool) {
            throw new SparkleException(String.format(messageTemplate, args));
        }
    }

    public static void raise(String messageTemplate, Object... args) {
        throw new SparkleException(String.format(messageTemplate, args));
    }

}
