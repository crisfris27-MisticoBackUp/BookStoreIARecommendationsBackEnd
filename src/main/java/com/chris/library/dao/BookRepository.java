package com.chris.library.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.chris.library.Projection.BookProjection;
import com.chris.library.entities.Book;


@RepositoryRestResource(excerptProjection = BookProjection.class)
public interface BookRepository extends JpaRepository<Book,Long>{
	
	Page<Book>findByTitleContaining(@Param("title")String title,
			Pageable pageable);
	
	Page<Book>findByCategory(@Param("category")String category,
			Pageable pageable);

	@Query("select o from Book o where id in :book_ids")
	List<Book> findBookByIdBooks(@Param("book_ids")List<Long> bookIdList);
	
	
}



