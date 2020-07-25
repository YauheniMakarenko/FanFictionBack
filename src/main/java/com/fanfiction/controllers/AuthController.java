package com.fanfiction.controllers;

import com.fanfiction.DTO.UserJwtDTO;
import com.fanfiction.payload.request.LoginRequest;
import com.fanfiction.payload.request.SignupRequest;
import com.fanfiction.repository.RoleRepository;
import com.fanfiction.repository.UserRepository;
import com.fanfiction.security.jwt.JwtUtils;
import com.fanfiction.service.UserService;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return userService.authenticateUser(loginRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        return userService.registerUser(signUpRequest);
    }

    @GetMapping("/activate/{code}")
    public void activate(@PathVariable String code, HttpServletResponse httpResponse) throws IOException {
        userService.activateUser(code);
        httpResponse.sendRedirect("https://fanfictionfront.herokuapp.com/");
    }

    @GetMapping("/authgoogle/{googlecode}")
    public ResponseEntity<UserJwtDTO> authGoogle(@PathVariable String googlecode) throws IOException {
        return userService.authGoogle(googlecode);
    }

    @GetMapping("/authvk/{vkcode}")
    public ResponseEntity<UserJwtDTO> authVk(@PathVariable String vkcode) throws ClientException, ApiException {
        return userService.authVk(vkcode);
    }

}
