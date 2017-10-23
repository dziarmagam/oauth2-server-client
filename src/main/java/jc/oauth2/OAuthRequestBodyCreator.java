package jc.oauth2;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.function.Function;

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

    private Function<OAuthRequestData, String> bodyCreator;

    OAuthRequestBodyCreator(OAuthRequestBodyType requestBodyType){
        switch (requestBodyType){
            case FORM:
                bodyCreator = this::createFormRequestBody;
                        break;
            case JSON:
                bodyCreator = this::createJsonRequestBody;
        }
    }

    String createRequestBody(OAuthRequestData oAuthRequestData){
        return bodyCreator.apply(oAuthRequestData);
    }

    private String createJsonRequestBody(OAuthRequestData data) {
        return String.format(JSON_TEMPLATE,
                data.clientId,
                data.clientSecret,
                data.code,
                data.redirectUri,
                data.responseType,
                data.grantType,
                data.state);
    }

    private String createFormRequestBody(OAuthRequestData data) {
        try {
            return String.format(QUERY_PARAM_TEMPATE,
                    URLEncoder.encode(data.clientId, "UTF-8"),
                    URLEncoder.encode(data.clientSecret, "UTF-8"),
                    URLEncoder.encode(data.code, "UTF-8"),
                    URLEncoder.encode(data.redirectUri, "UTF-8"),
                    URLEncoder.encode(data.responseType, "UTF-8"),
                    URLEncoder.encode(data.grantType, "UTF-8"),
                    URLEncoder.encode(data.state, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

    }


    enum OAuthRequestBodyType{
        JSON,
        FORM
    }
}
