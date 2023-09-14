package com.githubuserbranchesresttempapi.controller;

import com.githubuserbranchesresttempapi.controller.dto.BranchInfoResponseDto;
import com.githubuserbranchesresttempapi.controller.dto.GetGithubBranchResponseDto;
import com.githubuserbranchesresttempapi.controller.dto.RepositoryResponseDto;
import com.githubuserbranchesresttempapi.controller.error.InvalidAcceptHeaderException;
import com.githubuserbranchesresttempapi.controller.error.UsernameNotFoundException;
import com.githubuserbranchesresttempapi.domain.dto.UserNameResponseDto;
import com.githubuserbranchesresttempapi.domain.proxy.GithubProxy;
import com.githubuserbranchesresttempapi.domain.service.GithubService;
import com.githubuserbranchesresttempapi.domain.service.GithubServiceMapper;
import com.githubuserbranchesresttempapi.domain.service.GithubUsernameConverter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Log4j2
public class GithubRestController {
    private final GithubProxy githubClient;

    private final GithubUsernameConverter githubConverterService;
    private final GithubService githubService;

    private final GithubServiceMapper githubMapper;
    private final GithubRestControllerService githubRestControllerService;

    public GithubRestController(GithubProxy githubClient, GithubUsernameConverter githubConverterService, GithubService githubService, GithubServiceMapper githubMapper, GithubRestControllerService githubRestControllerService) {
        this.githubClient = githubClient;
        this.githubConverterService = githubConverterService;
        this.githubService = githubService;
        this.githubMapper = githubMapper;
        this.githubRestControllerService = githubRestControllerService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<RepositoryResponseDto>> getUserRepositories(@PathVariable String username, @RequestHeader("Accept") String acceptHeader) {
        if (acceptHeader.equals("application/xml")) {
            throw new InvalidAcceptHeaderException("Invalid Accept header");
        }

        List<UserNameResponseDto> userRepos = githubService.getReposName(username);
        if (userRepos == null) {
            throw new UsernameNotFoundException(username);
        }

        List<String> repoNames = githubConverterService.convertToRepoNames(userRepos);

        List<RepositoryResponseDto> repositoryResponseList = githubRestControllerService.fetchRepositoryResponses(username, repoNames);

        return ResponseEntity.ok(repositoryResponseList);
    }
}
