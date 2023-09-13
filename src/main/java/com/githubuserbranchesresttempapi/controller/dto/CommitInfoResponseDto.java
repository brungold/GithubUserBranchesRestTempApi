package com.githubuserbranchesresttempapi.controller.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CommitInfoResponseDto(String sha) {
}
