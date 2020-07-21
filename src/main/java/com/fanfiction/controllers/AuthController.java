package com.fanfiction.controllers;

import com.fanfiction.payload.request.LoginRequest;
import com.fanfiction.payload.request.SignupRequest;
import com.fanfiction.repository.RoleRepository;
import com.fanfiction.repository.UserRepository;
import com.fanfiction.security.jwt.JwtUtils;
import com.fanfiction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

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

}
