package com.githubuserbranchesresttempapi.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.githubuserbranchesresttempapi.domain.dto.UserNameResponseDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@Log4j2
public class GithubServiceMapper {
    private final ObjectMapper objectMapper;

    public GithubServiceMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<UserNameResponseDto> mapJsontoUserNameResponseDtoList(String json) {
        try {
            TypeReference<List<UserNameResponseDto>> typeReference = new TypeReference<>() {
            };
            return objectMapper.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            log.error("GithubSeviceMapper could not map json", e.getMessage());
            return Collections.emptyList();
        }
    }
}
