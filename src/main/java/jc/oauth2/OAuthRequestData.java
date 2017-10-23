package jc.oauth2;

class OAuthRequestData {

    final String clientId;
    final String clientSecret;
    final String code;
    final String redirectUri;
    final String state;

    OAuthRequestData(String clientId, String clientSecret,
                            String code, String redirectUri, String state) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.code = code;
        this.redirectUri = redirectUri;
        this.state = state;
    }

}
