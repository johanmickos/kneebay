package marketplace.repository.exceptions;

public class NotFoundException extends Exception
{
    public NotFoundException(String reason) {
        super(reason);
    }
}
