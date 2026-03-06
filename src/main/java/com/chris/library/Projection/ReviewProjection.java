package com.chris.library.Projection;

import java.util.Date;

import org.springframework.data.rest.core.config.Projection;
import com.chris.library.entities.Review;


@Projection(name = "ReviewProjection", types = { Review.class })
public interface ReviewProjection {
	Long getId();

	String getUserEmail();
	
	Date getDate();
	
	double getRating();
	
	long getBookId();
	
	String getReviewDescription();

}
