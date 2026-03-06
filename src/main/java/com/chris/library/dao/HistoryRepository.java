package com.chris.library.dao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.chris.library.Projection.HistoryProjection;
import com.chris.library.entities.History;

@RepositoryRestResource(excerptProjection = HistoryProjection.class)
public interface HistoryRepository extends JpaRepository<History, Long>{

	Page<History>findBooksByUserEmail(@Param("Email")String userEmail,Pageable pageable);
	
}
