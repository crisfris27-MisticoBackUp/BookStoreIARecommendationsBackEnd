package com.chris.library.dao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.chris.library.Projection.ReviewProjection;
import com.chris.library.entities.Review;

@RepositoryRestResource(excerptProjection = ReviewProjection.class)
public interface ReviewRepository extends JpaRepository<Review,Long>{
    
	Page<Review> findByBookId(@Param("book_id") Long bookId, Pageable pageable);
    
	Review findByUserEmailAndBookId(String userEmail, Long bookId);

}
