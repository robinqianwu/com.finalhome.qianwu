package de.jsf.application;

public class ApplicationException extends RuntimeException
{
    private static final long serialVersionUID = -2824979025128771200L;

    public static enum ErrorType {
        defaulterror, rightserror, internalError, implementationError, usageError
    }

    private ErrorType type;

    public ApplicationException(String msg)
    {
        super(msg);
    }

    public ApplicationException(String msg, Throwable e)
    {
        super(msg, e);
    }

    public ApplicationException(ErrorType type, String msg, Throwable e)
    {
        super("[type:" + type + "] " + msg, e);
        this.type = type;
    }

    public ApplicationException(ErrorType type, String msg)
    {
        super("[type:" + type + "] " + msg);
        this.type = type;
    }

    public ErrorType getType()
    {
        return type;
    }

    public void setType(ErrorType type)
    {
        this.type = type;
    }

}
