package marketplace.security.exceptions;

public class SessionException extends Exception
{
    public SessionException(String reason) {
        super(reason);
    }
}
