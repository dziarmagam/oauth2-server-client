package jc.endpoint;

import jc.github.GithubService;
import jc.oauth2.OAuthToken;
import jc.oauth2.OAuthTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class GithubEndpoint {

    @Autowired
    GithubService githubService;
    @Autowired
    OAuthTokenService oAuthTokenService;

    @RequestMapping("/github")
    public ResponseEntity getResource(HttpSession session){
        OAuthToken token = oAuthTokenService.getToken(session.getId());
        if(token == null){
            return ResponseEntity.ok(oAuthTokenService.getAuthenticationPage(session.getId()));
        }else{
            return ResponseEntity.ok(githubService.getGithubUserData(token));
        }
    }

}
