package no.dyrebar.dyrebar.classes;

public class Err
{
    private String code;
    private String message;

    public Err(String code, String message)
    {
        this.code = code;
        this.message = message;
    }

    public String getCode()
    {
        return code;
    }

    public String getMessage()
    {
        return message;
    }
}
