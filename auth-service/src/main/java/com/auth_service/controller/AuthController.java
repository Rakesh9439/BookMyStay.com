package com.auth_service.controller;


import com.auth_service.dto.APIResponse;
import com.auth_service.dto.LoginDto;
import com.auth_service.dto.UserDto;
import com.auth_service.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")

@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private AuthService authService;


    public AuthController(AuthService authService) {
        this.authService = authService;
    }




    //      http://localhost:8082/api/v1/auth/signup
    @PostMapping("/signup")
    public ResponseEntity<APIResponse<String>> signup( @Valid @RequestBody UserDto userDto){

        APIResponse<String> response = authService.signup(userDto);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
    }



    @PostMapping("/login")
    public ResponseEntity<APIResponse<String>> login( @Valid @RequestBody LoginDto loginDto){
        APIResponse<String> response = authService.login(loginDto);

        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }
}
