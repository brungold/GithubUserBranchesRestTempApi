package com.githubuserbranchesresttempapi;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Log4j2
public class GithubProxy {
    private final RestTemplate restTemplate;

    public GithubProxy(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${github-server.service.url}")
    String url;

    public String getUserRepos(String username) {
        //https://api.github.com -> /users/USERNAME/repos
        UriComponentsBuilder builder = UriComponentsBuilder
                .newInstance()
                .scheme("https")
                .host(url)
                .path("/users/" + username + "/repos");
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    builder.build().toUri(),
                    HttpMethod.GET,
                    null,
                    String.class
            );
            return response.getBody();
        } catch (RestClientResponseException exception) {
            log.error(exception.getStatusText() + " " + exception.getStatusCode().value());
        } catch (RestClientException exception) {
            log.error(exception.getMessage());
        }
        return null;
    }

    public String getBranches(String owner, String repo) {
        //https://api.github.com -> /repos/{owner}/{repo}/branches
        UriComponentsBuilder builder = UriComponentsBuilder
                .newInstance()
                .scheme("https")
                .host(url)
                .path("/repos/" + owner + "/" + repo + "/branches");
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    builder.build().toUri(),
                    HttpMethod.GET,
                    null,
                    String.class
            );
            return response.getBody();
        } catch (RestClientResponseException exception) {
            log.error(exception.getStatusText() + " " + exception.getStatusCode().value());
        } catch (RestClientException exception) {
            log.error(exception.getMessage());
        }
        return null;
    }
}


