package com.auth_service.service;

import com.auth_service.dto.APIResponse;
import com.auth_service.dto.LoginDto;
import com.auth_service.dto.UserDto;

public interface AuthService {

    APIResponse<String> signup(UserDto userDto);

    APIResponse<String> login(LoginDto loginDto);
}
