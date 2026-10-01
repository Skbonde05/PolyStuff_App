package myapp.org.userapp.data;

public class SupabaseRestResponse {
    private final int responseCode;
    private final String body;

    public SupabaseRestResponse(int responseCode, String body) {
        this.responseCode = responseCode;
        this.body = body;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getBody() {
        return body;
    }

    public boolean isSuccessful() {
        return responseCode >= 200 && responseCode < 300;
    }
}
