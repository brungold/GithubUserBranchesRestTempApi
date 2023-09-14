package com.githubuserbranchesresttempapi.controller.dto;

import org.springframework.http.HttpStatus;

public record ErrorUsernameResponseDto(HttpStatus httpStatus, String message) {
}
