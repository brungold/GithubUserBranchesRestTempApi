package com.githubuserbranchesresttempapi.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UserNameResponseDto(String name) {
}
