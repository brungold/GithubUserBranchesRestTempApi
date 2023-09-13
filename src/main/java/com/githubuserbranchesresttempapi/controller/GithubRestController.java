package com.githubuserbranchesresttempapi.controller;

import com.githubuserbranchesresttempapi.domain.proxy.GithubProxy;
import com.githubuserbranchesresttempapi.domain.service.GithubUsernameConverter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Log4j2
public class GithubRestController {
    private final GithubProxy githubClient;
    private final GithubUsernameConverter githubService;

    public GithubRestController(GithubProxy githubClient, GithubUsernameConverter githubService) {
        this.githubClient = githubClient;
        this.githubService = githubService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<RepositoryResponseDto>> getUserRepositories(@PathVariable String username, @RequestHeader("Accept") String acceptHeader) {

    }
}
