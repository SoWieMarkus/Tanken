package markus.wieland.tanken.api;

public class ApiURL {

    private String urlString;

    public ApiURL(String url) {
        this.urlString = url;
        this.urlString += "?";
    }

    public ApiURL append(String key, String value) {
        this.urlString += this.urlString.charAt(urlString.length() - 1) == '?' ? "" : "&";
        this.urlString += key + "=" + value;
        return this;
    }

    public <T> ApiURL append(String key, T value) {
        return append(key, String.valueOf(value));
    }

    @Override
    public String toString() {
        return urlString;
    }
}

