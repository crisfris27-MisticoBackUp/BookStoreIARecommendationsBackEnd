package com.chris.library.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chris.library.entities.Messages;
import com.chris.library.entities.Recomendation;
import com.chris.library.jwt.JWTtokens;
import com.chris.library.service.RecomendationService;

@CrossOrigin("http://localhost:3000")
@Controller
@RequestMapping("/api/Recomendation")
public class RecomendationController {

	@Autowired
	RecomendationService recomendationService;
	
	@Autowired
	private JWTtokens jwtUtil;
	
	
	private String extractTokenFromHeader(String authHeader) {
	    if (authHeader != null && authHeader.startsWith("Bearer ")) {
	        return authHeader.substring(7);
	    }
	    throw new RuntimeException("Invalid Authorization header");
	}
	
	@PostMapping("/savingRecomendation")
	@ResponseBody  // Add this
	public Recomendation saveMyRecomendation(@RequestHeader(value = "Authorization") String authHeader,
			@RequestBody Recomendation TheRecomenDAtionData) {
		Recomendation savingMyRecommendation =null;
		try {
			savingMyRecommendation = recomendationService.saveMyRecomendation(TheRecomenDAtionData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return savingMyRecommendation;
	}
	
	
	@GetMapping("/getUserPreferences")
	@ResponseBody
	public List<Recomendation> getUserPreferences(@RequestHeader(value = "Authorization") 
		String authHeader, @RequestParam String userEmail)throws Exception {
		
		
	    String token = extractTokenFromHeader(authHeader);
	    String Email = jwtUtil.extractUsername(token);
	    if(Email==null) {
			throw new Exception("User email is missing");
	    }
	    
	    List<Recomendation> preferences = recomendationService.getUserPreferences(userEmail);
	    return preferences;
	}
	
	
	
}
