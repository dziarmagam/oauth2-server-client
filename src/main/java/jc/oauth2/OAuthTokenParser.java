package jc.oauth2;

import jc.utils.BodyType;

interface OAuthTokenParser {

    OAuthToken parse(String body);

    static OAuthTokenParser of(BodyType bodyType){
        switch (bodyType){
            case FORM: return new FormOAuthTokenParser();
            case JSON: return new JsonOAuthTokenParser();
            default: throw new RuntimeException("No OAuth2 token parser found");
        }
    }

}

class FormOAuthTokenParser implements OAuthTokenParser{

    @Override
    public OAuthToken parse(String body) {
        String[] split = body.split("&");
        for (String param : split) {
            String[] keyValue = param.split("=");
            if("access_token".equals(keyValue[0])){
                return new OAuthToken(keyValue[1]);
            }
        }
        throw new RuntimeException("No OAuth 2 token found");
    }
}

class JsonOAuthTokenParser implements OAuthTokenParser{

    @Override
    public OAuthToken parse(String body) {
        return new OAuthToken(body.split("\"")[3]);
    }
}