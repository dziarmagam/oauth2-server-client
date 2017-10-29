package jc.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import jc.oauth2.OAuthTokenService;

@RestController("callback")
public class CallbackEndpoint {

    @Autowired
    private OAuthTokenService oAuthTokenService;


    @RequestMapping
    public ResponseEntity oauthCallback(@RequestParam("code") String code, @RequestParam("state") String state) throws IOException {

            oAuthTokenService.processAuthorizationCode(code, state);
            //send redirect
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/data");
            return new ResponseEntity<>(null, headers, HttpStatus.FOUND);

    }


}
