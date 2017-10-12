package jc.endpoint;

import jc.oauth2.OAuthRequestBody;
import jc.oauth2.OAuthTokenService;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.Charset;

@RestController("callback")
public class CallbackEndpoint {

    @Autowired
    OAuthTokenService oAuthTokenService;

    @RequestMapping
    public ResponseEntity oauthCallback(@RequestParam("code") String code, @RequestParam("state") String state) {
        try {
            OAuthRequestBody requestOAuthRequestBody = oAuthTokenService.createRequestOAuthRequestBody(code, state);
            //Get access token
            HttpPost httpPost = new HttpPost("https://github.com/login/oauth/access_token");
            System.out.println();
            httpPost.setEntity(new StringEntity(requestOAuthRequestBody.toJson(), Charset.forName("UTF-8")));
            httpPost.addHeader("Content-Type", "application/json");
            HttpClient httpClient = HttpClients.createDefault();
            HttpResponse execute = httpClient.execute(httpPost);
            String content = IOUtils.toString(execute.getEntity().getContent(), Charset.forName("UTF-8"));
            // extract token
            oAuthTokenService.extractToken(content, state);
            //send redirect
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/github");
            return new ResponseEntity<>(null, headers, HttpStatus.FOUND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
