package com.recruitment.task.github;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class GithubServiceImpl implements GithubService {

    private final Environment environment;
    private final RestTemplate restTemplate;

    @Override
    public GithubUser getGithubUserByName(String name) {
        val apiUrl = environment.getProperty("github.api-url");
        val requestUrl = apiUrl + name;

        return restTemplate.getForObject(requestUrl, GithubUser.class);
    }
}
