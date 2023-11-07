package com.recruitment.task.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder(toBuilder = true)
public record GithubUser(
        Long id,
        String login,
        String type,
        String name,
        @JsonProperty("avatar_url")
        String avatarUrl,
        @JsonProperty("created_at")
        LocalDateTime createdAt,
        @JsonProperty("followers")
        int followersCount,
        @JsonProperty("public_repos")
        int publicReposCount
) {
}
