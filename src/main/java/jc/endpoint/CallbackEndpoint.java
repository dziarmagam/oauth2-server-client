package jc.endpoint;

import com.google.gson.Gson;
import jc.oauth2.OAuthRequestBody;
import jc.oauth2.OAuthTokenService;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

@RestController("callback")
public class CallbackEndpoint {

    @Autowired
    OAuthTokenService oAuthTokenService;

    @Autowired
    Gson gson;

    @RequestMapping
    public ResponseEntity oauthCallback(@RequestParam("code") String code, @RequestParam("state") String state){
        OAuthRequestBody requestOAuthRequestBody = oAuthTokenService.createRequestOAuthRequestBody(code, state);
        HttpPost httpPost = new HttpPost("https://github.com/login/oauth/access_token");
        System.out.println();
        httpPost.setEntity(new StringEntity(gson.toJson(requestOAuthRequestBody), Charset.forName("UTF-8")));
        httpPost.addHeader("Content-Type","application/json");
        HttpClient httpClient = HttpClients.createDefault();

        try {
            HttpResponse execute = httpClient.execute(httpPost);
            String content = IOUtils.toString(execute.getEntity().getContent(), Charset.forName("UTF-8"));
            oAuthTokenService.extractToken(content, state);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/github");

            return new ResponseEntity<>(null,headers, HttpStatus.FOUND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
