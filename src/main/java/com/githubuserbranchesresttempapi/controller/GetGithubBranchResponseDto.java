package com.githubuserbranchesresttempapi.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.githubuserbranchesresttempapi.controller.dto.CommitInfoResponseDto;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GetGithubBranchResponseDto(String name, CommitInfoResponseDto commit) {
}
