package com.chris.library.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chris.library.RequestModels.AdminQuestionRequest;
import com.chris.library.entities.Messages;
import com.chris.library.jwt.JWTtokens;
import com.chris.library.service.MessagesService;

@CrossOrigin("http://localhost:3000")
@Controller
@RequestMapping("/api/messages")
@ResponseBody 
public class MessagesController {

	private MessagesService messageService;
	
	@Autowired
	private JWTtokens jwtUtil;

	
	@Autowired
	public MessagesController(MessagesService messagesService) {
		this.messageService=messagesService;
	}
	
	private String extractTokenFromHeader(String authHeader) {
	    if (authHeader != null && authHeader.startsWith("Bearer ")) {
	        return authHeader.substring(7);
	    }
	    throw new RuntimeException("Invalid Authorization header");
	}

	
	@PostMapping("/secure/add/message")
	public void postMEssage(@RequestHeader(value = "Authorization") String authHeader,
			@RequestBody Messages messageRequest)throws Exception {
		
		
	    String token = extractTokenFromHeader(authHeader);
	    String userEmail = jwtUtil.extractUsername(token);
	    if(userEmail==null) {
			throw new Exception("User email is missing");
	    }
	    
		messageService.PostMessage(messageRequest, userEmail);		
	}
	
	
	@PutMapping("/secure/admin/message")
	@ResponseBody
	public void putMessage(@RequestHeader(value="Authorization") String authHeader,
			@RequestBody AdminQuestionRequest AdminRequest) throws Exception {
		
	    String token = extractTokenFromHeader(authHeader);
	    String userEmail = jwtUtil.extractUsername(token);
	    if(userEmail==null) {
			throw new Exception("User email is missing");
	    }

	    System.out.println("AdminRequest.id = " + AdminRequest.getId());
	    System.out.println("AdminRequest.response = " + AdminRequest.getResponse());
	    messageService.PutMessage(AdminRequest, userEmail);
	    
	}
	
	
}
