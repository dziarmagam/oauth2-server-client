package jc.oauth2;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.function.Function;

import jc.utils.BodyType;

class OAuthRequestBodyCreator {

    private final static String JSON_TEMPLATE = "{" +
            "\"client_id\":\"%s\"," +
            "\"client_secret\":\"%s\"," +
            "\"code\":\"%s\"," +
            "\"redirect_uri\":\"%s\"," +
            "\"response_type\":\"%s\"," +
            "\"grant_type\":\"%s\"," +
            "\"state\":\"%s\"" +
            "}";

    private final static String QUERY_PARAM_TEMPATE =
            "client_id=%s&client_secret=%s&code=%s&redirect_uri=%s&response_type=%s&grant_type=%s&state=%s";

    private Function<OAuthRequestData, OAuth2RequestBody> bodyCreator;

    OAuthRequestBodyCreator(BodyType requestBodyType){
        switch (requestBodyType){
            case FORM:
                bodyCreator = this::createFormRequestBody;
                        break;
            case JSON:
                bodyCreator = this::createJsonRequestBody;
        }
    }

    OAuth2RequestBody createRequestBody(OAuthRequestData oAuthRequestData){
        return bodyCreator.apply(oAuthRequestData);
    }

    private OAuth2RequestBody createJsonRequestBody(OAuthRequestData data) {
        String body = String.format(JSON_TEMPLATE,
                data.clientId,
                data.clientSecret,
                data.code,
                data.redirectUri,
                data.responseType,
                data.grantType,
                data.state);
        return new OAuth2RequestBody(body, "application/json");
    }

    private OAuth2RequestBody createFormRequestBody(OAuthRequestData data) {
        try {
            String body = String.format(QUERY_PARAM_TEMPATE,
                    URLEncoder.encode(data.clientId, "UTF-8"),
                    URLEncoder.encode(data.clientSecret, "UTF-8"),
                    URLEncoder.encode(data.code, "UTF-8"),
                    URLEncoder.encode(data.redirectUri, "UTF-8"),
                    URLEncoder.encode(data.responseType, "UTF-8"),
                    URLEncoder.encode(data.grantType, "UTF-8"),
                    URLEncoder.encode(data.state, "UTF-8"));
            return new OAuth2RequestBody(body, "application/x-www-form-urlencoded");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

    }




    static class OAuth2RequestBody {
        public final String body;
        public final String contentType;

        public OAuth2RequestBody(String body, String contentType) {
            this.body = body;
            this.contentType = contentType;
        }

    }
}
