package com.fanfiction.service;

import com.fanfiction.models.User;
import com.fanfiction.DTO.UserJwtDTO;
import com.fanfiction.models.Role;
import com.fanfiction.repository.UserRepository;
import com.fanfiction.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void deleteUser(Long userId){
        userRepository.deleteById(userId);
    }

    public void setRole(Long userId, Role role){
        User user = userRepository.findById(userId).get();
        user.getRoles().clear();
        user.getRoles().add(role);
        userRepository.save(user);
    }

    public UserJwtDTO getUser(Long userId){
        User user = userRepository.findById(userId).get();
        return new UserJwtDTO(jwtUtils.generateJwtTokenByUsername(user.getUsername()),
                userId,
                user.getUsername(),
                user.getEmail(),
                new ArrayList<>(user.getRoles()).stream().map(role -> role.getName().name()).collect(Collectors.toList()));
    }
}
