package com.auth_service.service.impl;

import com.auth_service.constants.Role;
import com.auth_service.dto.APIResponse;
import com.auth_service.dto.LoginDto;
import com.auth_service.dto.UserDto;
import com.auth_service.entity.User;
import com.auth_service.repository.UserRepository;
import com.auth_service.service.AuthService;
import com.auth_service.service.JWTService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;

    private JWTService jwtService;

    private PasswordEncoder passwordEncoder;

    private AuthenticationManager authenticationManager;

    public AuthServiceImpl(UserRepository userRepository, JWTService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public APIResponse<String> signup(UserDto userDto) {



        // Check Email Exist or not

        if(userRepository.existsByEmail(userDto.getEmail())) {
            APIResponse<String> response = new APIResponse<>();
            response.setMessage("Registration Failed");
            response.setStatus(500);
            response.setData("User with Email Id exists");
            return response;
        }


        // Encode the password before saving that to the database

        String encryptedPassword = passwordEncoder.encode(userDto.getPassword());
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        user.setPassword(encryptedPassword);
        //  user.setRole("ROLE_USER");
        //  user.setRole("ROLE_ADMIN");
        user.setRole(Role.ROLE_USER);
        User savedUser = userRepository.save(user);
//        if (savedUser==null) {
//


        // Handle if saving fails
        if (savedUser == null) {
            throw new RuntimeException("User registration failed"); // Or use a custom exception
        }

        // finaliy save the user and return response as APIResponse


        APIResponse<String> response = new APIResponse<>();
        response.setMessage("Registration Done");
        response.setStatus(201);
        response.setData("User is registered");

        return response;






    }

    @Override
    public APIResponse<String> login(LoginDto loginDto) {

        APIResponse<String> response  = new APIResponse<>();

        UsernamePasswordAuthenticationToken token= new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(),
                loginDto.getPassword()
        );


        try{

            Authentication authenticate = authenticationManager.authenticate(token);
            if(authenticate.isAuthenticated()){

                String jwtToken  = jwtService.generateToken(loginDto.getEmail(),
                        authenticate.getAuthorities().iterator().next().getAuthority());
                response.setMessage("User Login successfull");
                response.setStatus(200);
                // response.setData("User has logged in");
                response.setData(jwtToken);
                return response;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        response.setMessage("Failed");
        response.setStatus(401);
        response.setData("Un-Authorized Access");

        return response;



    }
}
