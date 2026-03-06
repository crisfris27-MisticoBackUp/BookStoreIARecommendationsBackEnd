package com.chris.library.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.chris.library.dao.UserRepository;
import com.chris.library.entities.Role;
import com.chris.library.entities.User;
import com.chris.library.jwt.JWTtokens;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JWTtokens jwtUtil;  // Add this injection

    
    public User createUser(String username, String email, String password, String roleName) {
        // Create user
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(true);
        
        // Create role
        Role role = new Role();
        role.setRole(roleName);
        role.setUser(user);
        
        // Add role to user
        user.getRoles().add(role);
        
        // Save user (role will be saved automatically due to cascade)
        return userRepository.save(user);
    }
    
    
  //Authentication method
    public String authenticateUser(String username, String password) {
        // Find user by username
        Optional<User> userOptional = userRepository.findByUsername(username);
        
        if (!userOptional.isPresent()) {
            throw new RuntimeException("Invalid username or password");
        }
        
        User user = userOptional.get();
        
        // Check if user is enabled
        if (!user.getEnabled()) {
            throw new RuntimeException("Account is disabled");
        }
        
        // Validate password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }
        
        return jwtUtil.generateToken(username);
    }
    
	
}
