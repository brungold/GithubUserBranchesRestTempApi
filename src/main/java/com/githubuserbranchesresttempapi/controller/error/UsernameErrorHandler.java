package com.githubuserbranchesresttempapi.controller.error;

import com.githubuserbranchesresttempapi.controller.GithubRestController;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = GithubRestController.class)
@Log4j2
public class UsernameErrorHandler {
    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorUsernameResponseDto handleException(UsernameNotFoundException exception) {
        log.warn("UsernameNotFoundException while accessing username");
        return new ErrorUsernameResponseDto(HttpStatus.NOT_FOUND, "User not found");
    }
}
