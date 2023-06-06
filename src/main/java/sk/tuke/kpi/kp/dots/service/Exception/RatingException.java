package sk.tuke.kpi.kp.dots.service.Exception;

public class RatingException extends GameStudioException {
    public RatingException() {
    }

    public RatingException(String message) {
        super(message);
    }

    public RatingException(String message, Throwable cause) {
        super(message, cause);
    }

    public RatingException(Throwable cause) {
        super(cause);
    }
}
