package com.fanfiction.service;

import com.fanfiction.DTO.UserJwtDTO;
import com.fanfiction.models.ERole;
import com.fanfiction.models.Role;
import com.fanfiction.models.User;
import com.fanfiction.payload.request.EditNameRequest;
import com.fanfiction.payload.request.LoginRequest;
import com.fanfiction.payload.request.SignupRequest;
import com.fanfiction.payload.response.MessageResponse;
import com.fanfiction.repository.RoleRepository;
import com.fanfiction.repository.UserRepository;
import com.fanfiction.security.jwt.JwtUtils;
import com.fanfiction.security.securityservice.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private MailSender mailSender;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return UserDetailsImpl.build(user);
    }

    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        if (roles.contains("ROLE_USER") || roles.contains("ROLE_ADMIN")) {
            return ResponseEntity.ok(new UserJwtDTO(jwtUtils.generateJwtToken(authentication),
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    roles));
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: confirm email!"));
        }
    }

    public ResponseEntity<?> registerUser(SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        saveUser(new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword())));
        return ResponseEntity.ok(new MessageResponse("Please confirm Email!"));
    }

    private void saveUser(User user) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(ERole.ROLE_NOUSER).get());
        user.setRoles(roles);
        user.setActivationCode(UUID.randomUUID().toString());
        userRepository.save(user);
        String message = "Hello, " + user.getUsername() + "!" + "\n" +
                "Welcome to Fanfiction. Please visit the next link: https://fanfictionfback.herokuapp.com/api/auth/activate/"
                + user.getActivationCode();
        mailSender.send(user.getEmail(), "Activation code", message);
    }


    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);
        if (user == null) {
            return false;
        }
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(ERole.ROLE_USER).get());
        user.setRoles(roles);
        user.setActivationCode(null);
        userRepository.save(user);
        return true;
    }

    public ResponseEntity<?> editUserName(EditNameRequest editNameRequest) {
        if (userRepository.existsByUsername(editNameRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }
        User user = userRepository.findById(editNameRequest.getId()).get();
        user.setUsername(editNameRequest.getUsername());
        userRepository.save(user);
        return ResponseEntity.ok(new UserJwtDTO(jwtUtils.generateJwtTokenByUsername(user.getUsername())));
    }
}
