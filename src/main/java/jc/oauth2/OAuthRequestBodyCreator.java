package jc.oauth2;

import java.util.function.Function;

class OAuthRequestBodyCreator {

    private final static String JSON_TEMPLATE = "{" +
            "\"client_id\":\"%s\"," +
            "\"client_secret\":\"%s\"," +
            "\"code\":\"%s\"," +
            "\"redirect_uri \":\"%s\"," +
            "\"state\":\"%s\"" +
            "}";

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
        return String.format(JSON_TEMPLATE, data.clientId, data.clientSecret,
                data.code, data.redirectUri, data.state);
    }

    private String createFormRequestBody(OAuthRequestData oAuthRequestData) {
        return null;
    }


    enum OAuthRequestBodyType{
        JSON,
        FORM
    }
}
