package com.recruitment.task.github;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class GithubServiceImplTest {

    @Mock
    private Environment environment;

    @Mock
    private RestTemplate restTemplate;

    private GithubServiceImpl githubService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        githubService = new GithubServiceImpl(environment, restTemplate);
    }

    @Test
    public void givenGithubUserName_whenGetGithubUserByName_thenReturnGithubUser(){
        String apiUrl = "https://api.github.com/users/";
        GithubUser githubUser = new GithubUser(1L, "UserLogin", "User", "UserName",
                "https://example.com/avatar", LocalDateTime.of(2020, 12, 13, 14, 15), 2, 3);

        String requestUrl = apiUrl + githubUser.name();

        when(environment.getProperty(any())).thenReturn(apiUrl);
        when(restTemplate.getForObject(requestUrl, GithubUser.class)).thenReturn(githubUser);

        assertEquals(githubUser, githubService.getGithubUserByName(githubUser.name()));
    }
}