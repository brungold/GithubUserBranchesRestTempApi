package com.githubuserbranchesresttempapi.controller.dto;

import com.githubuserbranchesresttempapi.controller.dto.BranchInfoResponseDto;

import java.util.List;

public record RepositoryResponseDto(String repositoryName,
                                    String ownerLogin,
                                    List<BranchInfoResponseDto> branches) {
}
