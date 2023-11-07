package com.recruitment.task.service;

import com.recruitment.task.domain.User;
import com.recruitment.task.domain.UserRepository;
import com.recruitment.task.dto.UserDTO;
import com.recruitment.task.github.GithubService;
import com.recruitment.task.github.GithubUser;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final GithubService githubService;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDTO getUserByName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("User name cannot be null or empty");
        }

        val githubUser = githubService.getGithubUserByName(name);

        processUser(githubUser);

        val calculations = calculateCalculations(githubUser);

        val userDto = mapToUserDto(githubUser);
        userDto.setCalculations(calculations);

        return userDto;
    }

    private void processUser(GithubUser githubUser) {
        val user = userRepository.findByLogin(githubUser.login()).orElse(null);

        if (user == null) {
            val newUser = new User();
            newUser.setLogin(githubUser.login());
            userRepository.save(newUser);
        } else {
            user.incrementRequestCount();
        }
    }

    private double calculateCalculations(GithubUser githubUser) {
        if (githubUser.followersCount() != 0) {
            return (double) 6 / githubUser.followersCount() * (2 + githubUser.publicReposCount());
        } else {
            return Double.NaN;
        }
    }

    private UserDTO mapToUserDto(GithubUser githubUser) {
        val userDto = new UserDTO();
        userDto.setId(githubUser.id());
        userDto.setLogin(githubUser.login());
        userDto.setName(githubUser.name());
        userDto.setType(githubUser.type());
        userDto.setAvatarUrl(githubUser.avatarUrl());
        userDto.setCreatedAt(githubUser.createdAt());
        return userDto;
    }
}
