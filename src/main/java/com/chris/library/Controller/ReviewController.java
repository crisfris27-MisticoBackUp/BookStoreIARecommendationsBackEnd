package com.chris.library.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chris.library.RequestModels.ReviewRequest;
import com.chris.library.jwt.JWTtokens;
import com.chris.library.service.BookService;
import com.chris.library.service.ReviewService;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final BookService bookService;

	@Autowired
	private JWTtokens jwtUtil;
	
	@Autowired
	private ReviewService reviewService;
	
	public ReviewController(ReviewService reviewservice, BookService bookService) {
		this.reviewService=reviewservice;
		this.bookService = bookService;
	}
	
	private String extractTokenFromHeader(String authHeader) {
	    if (authHeader != null && authHeader.startsWith("Bearer ")) {
	        return authHeader.substring(7);
	    }
	    throw new RuntimeException("Invalid Authorization header");
	}
	
	
	@PostMapping("/secure")
	public void postReview(@RequestHeader(value = "Authorization") String authHeader,
			@RequestBody ReviewRequest reviewrequest)throws Exception {
	    String token = extractTokenFromHeader(authHeader);
	    String userEmail = jwtUtil.extractUsername(token);
		if(userEmail==null) {
			throw new Exception("User email is missing");
		}
		reviewService.postReview(userEmail, reviewrequest);
		
	}
	
	
	@GetMapping("/secure/user/book")
	public Boolean reviewBookByUser(@RequestHeader(value="Authorization") String authHeader,
			@RequestParam Long bookId) throws Exception{
		
	    String token = extractTokenFromHeader(authHeader);
	    String userEmail = jwtUtil.extractUsername(token);
	    if(userEmail==null) {
			throw new Exception("User email is missing");
	    }
		return reviewService.userReviewListed(userEmail, bookId);
	}
	
	
	
	@PutMapping("/secure/renew/loan")
	public void renewloan(@RequestHeader(value="Authorization") String authHeader,
			@RequestParam Long bookId) throws Exception{
		
	    String token = extractTokenFromHeader(authHeader);
	    String userEmail = jwtUtil.extractUsername(token);
	    if(userEmail==null) {
			throw new Exception("User email is missing");
	    }
	    
	    bookService.renewLoan(userEmail, bookId);
	}
	
	
	
	
}
