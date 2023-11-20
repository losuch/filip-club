package de.mlosoft.filipclub.error;

public class FilipClubException extends RuntimeException {

    private static final long serialVersionUID = 8376991589861175106L;
    private final ErrorInfo errorInfo;

    public FilipClubException(ErrorInfo errorInfo) {
        super();
        this.errorInfo = errorInfo;
    }

    public FilipClubException(ErrorInfo errorInfo, Exception e) {
        super(e);
        this.errorInfo = errorInfo;
    }

    public ErrorInfo getErrorInfo() {
        return errorInfo;
    }

}
