package com.chris.library.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.chris.library.Projection.CheckoutProjection;
import com.chris.library.entities.Checkout;

@RepositoryRestResource(excerptProjection = CheckoutProjection.class)
public interface CheckoutRepository extends JpaRepository<Checkout,Long>{
	
	@Query("SELECT c FROM Checkout c WHERE c.userEmail = :userEmail AND c.bookId = :bookId")
	Checkout findByUserEmailAndBookId(@Param("userEmail") String userEmail, @Param("bookId") Long bookId);
	
	
	List<Checkout>findBooksByUserEmail(String userEmail);
	
}
