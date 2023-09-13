package com.githubuserbranchesresttempapi.domain.service;

import com.githubuserbranchesresttempapi.domain.dto.UserNameResponseDto;
import com.githubuserbranchesresttempapi.domain.proxy.GithubProxy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GithubUsernameConverter {
    public final GithubProxy githubClient;

    public GithubUsernameConverter(GithubProxy githubClient) {
        this.githubClient = githubClient;
    }

    public List<String> convertToRepoNames(List<UserNameResponseDto> repos) {
        List<String> repoNames = repos.stream()
                .map(UserNameResponseDto::name)
                .collect(Collectors.toList());

        return repoNames;
    }
}
