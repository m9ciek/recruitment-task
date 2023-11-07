package com.recruitment.task.service;

import com.recruitment.task.domain.UserRepository;
import com.recruitment.task.dto.UserDTO;
import com.recruitment.task.github.GithubService;
import com.recruitment.task.github.GithubUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


class UserServiceImplTest {

    @Mock
    private GithubService githubService;

    @Mock
    private UserRepository userRepository;

    private UserServiceImpl userService;

    private static GithubUser githubUser;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(githubService, userRepository);

        githubUser = new GithubUser(1L, "UserLogin", "User", "UserName",
                "https://example.com/avatar",
                LocalDateTime.of(2020, 12, 13, 14, 15), 2, 3);
    }

    @Test
    public void givenUserLogin_whenGetUserByLogin_thenReturnCorrespondingUserDTO() {
        String validLogin = "UserLogin";
        when(githubService.getGithubUserByLogin(validLogin)).thenReturn(githubUser);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setLogin("UserLogin");
        userDTO.setName("UserName");
        userDTO.setType("User");
        userDTO.setAvatarUrl("https://example.com/avatar");
        userDTO.setCreatedAt(LocalDateTime.of(2020, 12, 13, 14, 15));
        userDTO.setCalculations(15);

        assertEquals(userDTO, userService.getUserByLogin(validLogin));
    }

    @Test
    public void givenUserLogin_whenGetUserByLogin_thenReturnUserDTOWithThatLogin() {
        String validLogin = "UserLogin";
        when(githubService.getGithubUserByLogin(validLogin)).thenReturn(githubUser);

        UserDTO userDTO = new UserDTO();
        userDTO.setLogin(validLogin);

        assertEquals(userDTO.getLogin(), userService.getUserByLogin(validLogin).getLogin());
    }

    @Test
    public void givenAnyGithubUserLoginWithFollowers_whenGetUserByLogin_thenReturnUserWithValidCalculationResult() {
        double validCalculationResult = 15;
        when(githubService.getGithubUserByLogin(any())).thenReturn(githubUser);

        UserDTO result = userService.getUserByLogin("Random");

        assertEquals(validCalculationResult, result.getCalculations());
    }

    @Test
    public void givenGithubUserWithZeroFollowers_whenGetUserByLogin_thenReturnUserWithNaNCalculationResult() {
        double nanCalculationResult = Double.NaN;
        GithubUser githubUserWithZeroFollowers = githubUser.toBuilder().followersCount(0).build();

        when(githubService.getGithubUserByLogin(any())).thenReturn(githubUserWithZeroFollowers);

        UserDTO result = userService.getUserByLogin("Zeroer");

        assertEquals(nanCalculationResult, result.getCalculations());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void givenNullOrEmptyLogin_whenGetUserByLogin_thenThrowException(String invalidLogin) {
        assertThrows(IllegalArgumentException.class, () -> userService.getUserByLogin(invalidLogin));
    }
}
