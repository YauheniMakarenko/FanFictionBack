package com.fanfiction.controllers;

import com.fanfiction.models.ERole;
import com.fanfiction.DTO.UserJwtDTO;
import com.fanfiction.models.Role;
import com.fanfiction.models.User;
import com.fanfiction.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fanfic")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("getAllUsers")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {
        return adminService.getAllUsers();
    }

    @DeleteMapping("deleteuser/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@PathVariable Long userId) {
        adminService.deleteUser(userId);
    }

    @PostMapping("setadmin")
    @PreAuthorize("hasRole('ADMIN')")
    public void setAdminRole(@RequestBody Long userId) {
        adminService.setRole(userId, ERole.ROLE_ADMIN);
    }

    @PostMapping("blockuser")
    @PreAuthorize("hasRole('ADMIN')")
    public void blockUser(@RequestBody Long userId) {
        adminService.setRole(userId, ERole.ROLE_NOUSER);
    }

    @PostMapping("setuserrole")
    @PreAuthorize("hasRole('ADMIN')")
    public void setUserRole(@RequestBody Long userId) {
        adminService.setRole(userId, ERole.ROLE_USER);
    }

    @GetMapping("getuser/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserJwtDTO getUser(@PathVariable Long userId) {
        return adminService.getUser(userId);
    }

}
