package com.githubuserbranchesresttempapi.controller;

import com.githubuserbranchesresttempapi.controller.dto.BranchInfoResponseDto;
import com.githubuserbranchesresttempapi.controller.dto.GetGithubBranchResponseDto;
import com.githubuserbranchesresttempapi.controller.dto.RepositoryResponseDto;
import com.githubuserbranchesresttempapi.domain.proxy.GithubProxy;
import com.githubuserbranchesresttempapi.domain.service.GithubService;
import com.githubuserbranchesresttempapi.domain.service.GithubServiceMapper;
import com.githubuserbranchesresttempapi.domain.service.GithubUsernameConverter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class GithubRestControllerService {
    private final GithubProxy githubClient;
    private final GithubUsernameConverter githubConverterService;
    private final GithubService githubService;
    private final GithubServiceMapper githubMapper;

    public GithubRestControllerService(GithubProxy githubClient, GithubUsernameConverter githubConverterService, GithubService githubService, GithubServiceMapper githubMapper) {
        this.githubClient = githubClient;
        this.githubConverterService = githubConverterService;
        this.githubService = githubService;
        this.githubMapper = githubMapper;
    }

    public List<RepositoryResponseDto> fetchRepositoryResponses(String username, List<String> repoNames) {
        List<RepositoryResponseDto> repositoryResponseList = new ArrayList<>();

        for (String repoName : repoNames) {
            List<GetGithubBranchResponseDto> branches = fetchBranches(username, repoName);
            if (branches != null) {
                List<BranchInfoResponseDto> branchInfoList = createBranchInfoList(branches);
                RepositoryResponseDto repositoryResponseDto = createRepositoryResponse(repoName, username, branchInfoList);
                repositoryResponseList.add(repositoryResponseDto);
            }
        }

        return repositoryResponseList;
    }

    private List<GetGithubBranchResponseDto> fetchBranches(String username, String repoName) {
        String json = githubClient.getBranches(username, repoName);
        List<GetGithubBranchResponseDto> branches = githubMapper.mapJsonToGetGithubBranchResponseDto(json);
        return branches;
    }

    private List<BranchInfoResponseDto> createBranchInfoList(List<GetGithubBranchResponseDto> branches) {
        return branches.stream()
                .map(branch -> new BranchInfoResponseDto(branch.name(), branch.commit().sha()))
                .collect(Collectors.toList());
    }

    private RepositoryResponseDto createRepositoryResponse(String repoName, String username, List<BranchInfoResponseDto> branchInfoList) {
        return new RepositoryResponseDto(repoName, username, branchInfoList);
    }
}

