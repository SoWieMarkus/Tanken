package markus.wieland.tanken.api.models.responses;

public abstract class Response {

    private ResponseStatus status;
    private boolean ok;
    private String message;
    private String license;
    private String data;

    public ResponseStatus getResponseStatus() {
        return status;
    }

    public boolean isOk() {
        return ok;
    }

    public String getMessage() {
        return message;
    }

    public String getLicense() {
        return license;
    }

    public String getData() {
        return data;
    }
}
