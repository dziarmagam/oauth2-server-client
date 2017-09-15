package jc.oauth2;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@Component
public class OAuthTokenService {

    private final Map<String, OAuthToken> sessionTokenMap;

    @Value("${client.id}")
    String clientId;
    @Value("${client.secret}")
    String clientSecret;
    @Value("${authentication.uri}")
    String authenticationUrl;
    @Value("${redirect_uri}")
    String redirectUri;
    @Value("${scope}")
    String scope;

    public OAuthTokenService() {
        this.sessionTokenMap = new HashMap<>();
    }

    public OAuthToken getToken(String session) {
        return sessionTokenMap.get(session);
    }

    public void extractToken(String responseContent, String state){
        String[] split = responseContent.split("&");
        for (String param : split) {
            String[] keyValue = param.split("=");
            if("access_token".equals(keyValue[0])){
                sessionTokenMap.put(state, new OAuthToken(keyValue[1]));
            }
        }
    }

    public String getAuthenticationPage(String state) {
        InputStream resourceAsStream = this.getClass().getResourceAsStream("/index.html");

        try {
            return IOUtils.toString(resourceAsStream,
                    Charset.forName("UTF-8")).replace("{authenticationUrl}",
                    buildAuthenticationUrl(state));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public OAuthRequestBody createRequestOAuthRequestBody(String code, String state){
        return new OAuthRequestBody(clientId, clientSecret, code, redirectUri, state);
    }

    private CharSequence buildAuthenticationUrl(String state) {
        return authenticationUrl +
                String.format("?client_id=%s&redirect_uri=%s&scope=%s&state=%s",
                        clientId, redirectUri, scope, state);
    }
}
