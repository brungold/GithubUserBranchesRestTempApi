package com.githubuserbranchesresttempapi.dto;

import com.githubuserbranchesresttempapi.GithubProxy;
import com.githubuserbranchesresttempapi.GithubServiceMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class GihubService {
    private final GithubProxy githubClient;
    private final GithubServiceMapper githubServiceMapper;

    public GihubService(GithubProxy githubClient, GithubServiceMapper githubServiceMapper) {
        this.githubClient = githubClient;
        this.githubServiceMapper = githubServiceMapper;
    }

    public List<UserNameResponseDto> getReposName(String username) {
        String json = githubClient.getUserRepos(username);
        if (json == null) {
            log.error("JSON response was null");
            return null;
        }
        List<UserNameResponseDto> userNameResponseDtoList = githubServiceMapper.mapJsontoUserNameResponseDtoList(json);
        log.info(userNameResponseDtoList);

        return userNameResponseDtoList;
    }
}
