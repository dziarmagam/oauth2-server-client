package jc.github;

import jc.oauth2.OAuthToken;
import jc.oauth2.OAuthTokenService;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class GithubService {

    @Value("${github.api.uri}")
    String githubApiUrl;

    public String getGithubUserData(OAuthToken oAuthToken){
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(githubApiUrl + "/user");
        httpGet.addHeader("Authorization", "token " + oAuthToken.token);
        try {
            HttpResponse execute = httpClient.execute(httpGet);
            return IOUtils.toString(execute.getEntity().getContent(), "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
