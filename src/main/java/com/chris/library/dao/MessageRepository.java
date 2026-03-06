package com.chris.library.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.chris.library.Projection.MessagesProjection;
import com.chris.library.entities.Messages;

@RepositoryRestResource(path = "messages", excerptProjection = MessagesProjection.class)
public interface MessageRepository extends JpaRepository<Messages, Long>{
    
    Page<Messages> findByUserEmail(@Param("userEmail") String userEmail, Pageable pageable);
    
    Page<Messages> findByClosed(@Param("closed") boolean closed, Pageable pageable);

}