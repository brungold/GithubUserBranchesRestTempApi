package com.githubuserbranchesresttempapi.controller;

import com.githubuserbranchesresttempapi.controller.dto.BranchInfoResponseDto;
import com.githubuserbranchesresttempapi.controller.dto.GetGithubBranchResponseDto;
import com.githubuserbranchesresttempapi.controller.dto.RepositoryResponseDto;
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

    public GithubRestController(GithubProxy githubClient, GithubUsernameConverter githubConverterService, GithubService githubService, GithubServiceMapper githubMapper) {
        this.githubClient = githubClient;
        this.githubConverterService = githubConverterService;
        this.githubService = githubService;
        this.githubMapper = githubMapper;
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<RepositoryResponseDto>> getUserRepositories(@PathVariable String username, @RequestHeader("Accept") String acceptHeader) {
        if (acceptHeader.equals("application/xml")) {
            throw new InvalidAcceptHeaderException("Invalid Accept header");
        }

        List<UserNameResponseDto> userRepos = githubService.getReposName(username);
        if(userRepos == null){
            throw new UsernameNotFoundException(username);
        }

        List<String> repoNames = githubConverterService.convertToRepoNames(userRepos);

        List<RepositoryResponseDto> repositoryResponseList = fetchRepositoryResponses(username, repoNames);

        return ResponseEntity.ok(repositoryResponseList);
    }

    private List<RepositoryResponseDto> fetchRepositoryResponses(String username, List<String> repoNames) {
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
