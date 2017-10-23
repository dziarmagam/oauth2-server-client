package jc.github;

import jc.oauth2.OAuthToken;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DataService {

    @Value("${data.api.uri}")
    private String dataApiUrl;

    public String getData(OAuthToken oAuthToken){
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(dataApiUrl);
        httpGet.addHeader("Authorization", "token " + oAuthToken.token);
        try {
            HttpResponse execute = httpClient.execute(httpGet);
            return IOUtils.toString(execute.getEntity().getContent(), "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
