package com.chris.library.service;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.chris.library.RequestModels.ReviewRequest;
import com.chris.library.dao.BookRepository;
import com.chris.library.dao.ReviewRepository;
import com.chris.library.entities.Review;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ReviewService {

	private BookRepository bookRepository;
	private ReviewRepository reviewrepository;
	public ReviewService(BookRepository bookRepository, ReviewRepository reviewRepository) {
		this.bookRepository=bookRepository;
		this.reviewrepository=reviewRepository;
	}
	
	
	
	
	public void postReview(String useremail, ReviewRequest reviewRequest) throws Exception {
		Review validateReview = reviewrepository.findByUserEmailAndBookId(useremail, 
				reviewRequest.getBookId());
		if(validateReview !=null) {
			throw new Exception("Review already created");
		}
		Review review = new Review();
		review.setBookId(reviewRequest.getBookId());
		review.setRating(reviewRequest.getRating());
		review.setUserEmail(useremail);
		if(reviewRequest.getReviewDescription().isPresent()) {
			review.setReviewDescription(reviewRequest.getReviewDescription().map(
						Object::toString
					).orElse(null));
		}
		review.setDate(Date.valueOf(LocalDate.now()));
		reviewrepository.save(review);
	}
	
	
	
	public Boolean userReviewListed(String userEmail, Long bookId) throws Exception {
		Review validateReview = reviewrepository
				.findByUserEmailAndBookId(userEmail, bookId);
		
		if(validateReview != null) {
			return true;
		}else {
			return false;
		}
		
	}
	
	
	
	
	
	
	

}
