package ie.tudublin;

public class VisualException extends Throwable
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    private String message;
    
    public VisualException(String message)
    {
        this.message = message;
    }

    public String toString()
    {
        return message;
    }
}