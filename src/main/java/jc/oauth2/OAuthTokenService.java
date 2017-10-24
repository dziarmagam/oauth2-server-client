package jc.oauth2;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import jc.utils.BodyType;

import static jc.oauth2.OAuthRequestBodyCreator.*;

@Component
public class OAuthTokenService {

    @Value("${oauth2.client.id}")
    private String clientId;
    @Value("${oauth2.client.secret}")
    private String clientSecret;
    @Value("${oauth2.authentication.uri}")
    private String authenticationUrl;
    @Value("${oauth2.redirect_uri}")
    private String redirectUri;
    @Value("${oauth2.scope}")
    private String scope;
    @Value("${oauth2.access.token.endpoint}")
    private String accessTokenEndpoint;
    @Value("${oauth2.request.body.type}")
    private String oAuth2RequestBodyType;
    @Value("${oauth2.token.body.type}")
    private String oAuth2TokenBodyType;

    private final Map<String, OAuthToken> sessionTokenMap;
    private OAuthRequestBodyCreator oAuthRequestBodyCreator;
    private OAuthTokenParser oAuthTokenParser;

    public OAuthTokenService() {

        this.sessionTokenMap = new HashMap<>();
    }

    @PostConstruct
    void init(){
        this.oAuthRequestBodyCreator =
                new OAuthRequestBodyCreator(BodyType.valueOf(oAuth2RequestBodyType));
        this.oAuthTokenParser = OAuthTokenParser.of(BodyType.valueOf(oAuth2TokenBodyType));
    }

    public OAuthToken getToken(String session) {
        return sessionTokenMap.get(session);
    }

    public void processAuthoricationCode(String code, String state) throws IOException {
        OAuthRequestData requestOAuthRequestData = createRequestOAuthRequestBody(code, state);
        //Get access token
        HttpResponse execute = requestAccessToken(requestOAuthRequestData);
        String content = IOUtils.toString(execute.getEntity().getContent(), Charset.forName("UTF-8"));
        // extract token
        extractToken(content, state);
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

    private HttpResponse requestAccessToken(OAuthRequestData requestOAuthRequestData) throws IOException {
        HttpPost httpPost = new HttpPost(accessTokenEndpoint);
        OAuth2RequestBody requestBody = oAuthRequestBodyCreator.createRequestBody(requestOAuthRequestData);
        httpPost.setEntity(new StringEntity(requestBody.body, Charset.forName("UTF-8")));
        httpPost.addHeader("Content-Type", requestBody.contentType);
        HttpClient httpClient = HttpClients.createDefault();
        return httpClient.execute(httpPost);
    }

    private void extractToken(String responseContent, String state){
        OAuthToken token = oAuthTokenParser.parse(responseContent);
        sessionTokenMap.put(state, token);
    }

    private OAuthRequestData createRequestOAuthRequestBody(String code, String state){
        return new OAuthRequestData(clientId, clientSecret, code, redirectUri, state,
                "access_token", "authorization_code");
    }

    private CharSequence buildAuthenticationUrl(String state) {
        return authenticationUrl +
                String.format("?client_id=%s&redirect_uri=%s&scope=%s&state=%s&response_type=code",
                        clientId, redirectUri, scope, state);
    }
}
