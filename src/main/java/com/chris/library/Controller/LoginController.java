package com.chris.library.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chris.library.entities.User;
import com.chris.library.service.UserService;

@RestController
@RequestMapping("/api/books")
public class LoginController {

	@Autowired
	public UserService userService;
	
	@PostMapping("/auth/CreateUser")
	public ResponseEntity<?> registerUser(@RequestParam String username,
            @RequestParam String email,
            @RequestParam String password) {
		try {
			User newUser = userService.createUser(username, email, password, "USER");
			return ResponseEntity.ok().body(Map.of(
					"message", "User created successfully",
					"username", newUser.getUsername()
					));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(Map.of(
					"error", "User creation failed: " + e.getMessage()
					));
		}
	}
	
	@PostMapping("/auth/login")
	public ResponseEntity<?> LoginUser(@RequestParam String username,
            @RequestParam String password) {
		
		try {
			String myToken = userService.authenticateUser(username, password);
	        return ResponseEntity.ok().body(Map.of(
	            "token", myToken,
	            "type", "Bearer",
	            "username", username
	        ));
			
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(Map.of(
					"error","User login failed, either password or username is incorrect: "
							+ e.getMessage()
					));
		}
		
	
	}
}