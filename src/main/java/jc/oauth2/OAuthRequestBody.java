package jc.oauth2;

public class OAuthRequestBody {
    public final String client_id;
    public final String client_secret;
    public final String code;
    public final String redirect_uri;
    public final String state;

    public OAuthRequestBody(String client_id, String client_secret,
                            String code, String redirect_uri, String state) {
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.code = code;
        this.redirect_uri = redirect_uri;
        this.state = state;
    }
}
