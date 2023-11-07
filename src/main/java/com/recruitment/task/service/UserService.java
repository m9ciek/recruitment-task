package com.recruitment.task.service;

import com.recruitment.task.dto.UserDTO;

public interface UserService {
    UserDTO getUserByName(String name);
}
