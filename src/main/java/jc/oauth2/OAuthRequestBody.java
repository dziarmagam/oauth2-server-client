package jc.oauth2;

public class OAuthRequestBody {

    private final static String JSON_TEMPLATE = "{" +
            "\"client_id\":\"%s\"," +
            "\"client_secret\":\"%s\"," +
            "\"code\":\"%s\"," +
            "\"redirect_uri \":\"%s\"," +
            "\"state\":\"%s\"" +
            "}";

    public final String clientId;
    public final String clientSecret;
    public final String code;
    public final String redirectUri;
    public final String state;

    public OAuthRequestBody(String clientId, String clientSecret,
                            String code, String redirectUri, String state) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.code = code;
        this.redirectUri = redirectUri;
        this.state = state;
    }

    public String toJson(){
        return String.format(JSON_TEMPLATE, clientId, clientSecret, code, redirectUri, state);
    }
}
