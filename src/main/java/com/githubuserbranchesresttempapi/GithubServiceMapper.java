package com.githubuserbranchesresttempapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class GithubServiceMapper {
    private final ObjectMapper objectMapper;

    public GithubServiceMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
}
